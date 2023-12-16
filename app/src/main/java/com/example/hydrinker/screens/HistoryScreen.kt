package com.example.hydrinker.screens

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hydrinker.database.HydrationData
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.services.HydrationViewModel
import com.example.hydrinker.services.HydrationViewModelFactory
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.AxisRenderer
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun HistoryScreen(navController: NavController, context: Context = LocalContext.current) {
    val hydrationViewModel: HydrationViewModel =
        viewModel(factory = HydrationViewModelFactory(context))
    val hydrationDataList by hydrationViewModel.getHydrationData()
        .observeAsState(initial = emptyList())

    val chartModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(key1 = hydrationDataList) {
        val formattedEntries = getFormattedEntries(hydrationDataList)
        if (formattedEntries.isEmpty()) {
            println("Formatted entries list is empty. Skipping chart update.")
        } else {
            println("Formatted Entries: $formattedEntries")
            chartModelProducer.setEntries(formattedEntries)
        }
    }

    ScreenHeader(headerText = "History")
    Card(
        modifier = Modifier
            .padding(8.dp, 60.dp, 24.dp, 128.dp) ,// start - top - end - bottom

        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Text("Track your past drinking behaviour here:",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Vico LineChart with dynamic data using ChartEntryModelProducer
        Chart(
            chart = lineChart(),
            chartModelProducer = chartModelProducer,
            startAxis = createStartAxis(),
            bottomAxis = rememberBottomAxis(valueFormatter = createDateFormatAxisValueFormatter()),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}


private fun getFormattedEntries(entries: List<HydrationData>): List<FloatEntry> {
    // Use the current date as the reference date
    val referenceDate = LocalDate.now()

    // Use a map to accumulate amounts for each date
    val accumulatedAmounts = mutableMapOf<LocalDate, Float>()

    entries.forEach { entry ->
        val parsedDate: LocalDate = entry.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        accumulatedAmounts[parsedDate] = (accumulatedAmounts[parsedDate] ?: 0f) + entry.amountInMillilitres.toFloat()
    }

    // Sort the map entries by date to ensure correct order
    val sortedEntries = accumulatedAmounts.entries.sortedBy { it.key }
    println("sortedEntries: $sortedEntries")
    return sortedEntries.flatMap { (parsedDate, accumulatedAmount) ->
        listOf(
            entryOf(
                    x = (referenceDate.toEpochDay() - parsedDate.toEpochDay()).toFloat(),
                    y = accumulatedAmount

            )
        )
    }
}



@Composable
private fun createStartAxis(): AxisRenderer<AxisPosition.Vertical.Start> {
    val customAxisValues = listOf(0f, 1000f, 2000f, 3000f)

    return rememberStartAxis(
        valueFormatter = createCustomAxisValueFormatter(customAxisValues),
    ).apply {
        // Customize other properties as needed
        // For example, you can set custom label formatting, ticks, etc.
    }
}


private fun createCustomAxisValueFormatter(customAxisValues: List<Float>): AxisValueFormatter<AxisPosition.Vertical.Start> {
    return AxisValueFormatter { value, _ ->
        // Convert the scaled value to the actual volume
        val actualVolume = (value / customAxisValues.last()) * 3000f
        "${actualVolume.toInt()} ml"
    }
}

private fun createDateFormatAxisValueFormatter(): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")

    return AxisValueFormatter { value, _ ->
        // Log the input value for debugging
        println("Input value for Axis: $value")

        // Convert the value back to LocalDate and format it using the specified pattern
        val epochDay = value.toLong()
        val date = LocalDate.ofEpochDay(epochDay)
        println("epochDay $epochDay")
        println("date $date")
        // Log the formatted date for debugging
        println("Formatted date for Axis: ${date.format(dateTimeFormatter)}")

        date.format(dateTimeFormatter)
    }
}

