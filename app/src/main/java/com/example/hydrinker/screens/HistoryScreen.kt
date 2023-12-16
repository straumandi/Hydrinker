package com.example.hydrinker.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavController, context: Context = LocalContext.current) {
    val hydrationViewModel: HydrationViewModel =
        viewModel(factory = HydrationViewModelFactory(context))
    val hydrationDataList by hydrationViewModel.getHydrationData()
        .observeAsState(initial = emptyList())

    val chartModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(key1 = hydrationDataList) {
        println(hydrationDataList)
        chartModelProducer.setEntries(getFormattedEntries(hydrationDataList))
    }

    Column(
        modifier = Modifier
            .padding(8.dp, 16.dp, 24.dp, 248.dp) // start - top - end - bottom
    ) {
        Text("History", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Vico LineChart with dynamic data using ChartEntryModelProducer
        Chart(
            chart = lineChart(),
            chartModelProducer = chartModelProducer,
            startAxis = createStartAxis(),
            bottomAxis = rememberBottomAxis(valueFormatter = createDateFormatAxisValueFormatter()),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

private fun getFormattedEntries(entries: List<HydrationData>): List<FloatEntry> {
    val referenceDate = entries.minOfOrNull { it.date.time } ?: 0L
    val referenceDay = LocalDate.ofEpochDay(referenceDate / (24 * 60 * 60 * 1000)).toEpochDay()

    // Use a map to accumulate amounts for each date
    val accumulatedAmounts = mutableMapOf<LocalDate, Float>()

    // Parse the date string from the HydrationData object using DateTimeFormatter
    val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    entries.forEach { entry ->
        val parsedDate: LocalDate = LocalDateTime.parse(entry.date.toString(), dateTimeFormatter).toLocalDate()

        accumulatedAmounts[parsedDate] = (accumulatedAmounts[parsedDate] ?: 0f) + entry.amountInMillilitres.toFloat()
    }

    return accumulatedAmounts.map { (parsedDate, accumulatedAmount) ->
        entryOf(
            x = ChronoUnit.DAYS.between(LocalDate.ofEpochDay(referenceDay), parsedDate).toFloat(),
            y = accumulatedAmount
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
        // Convert the value back to LocalDate and format it using the specified pattern
        val date = Instant.ofEpochMilli(value.toLong())
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        date.format(dateTimeFormatter)
    }
}






