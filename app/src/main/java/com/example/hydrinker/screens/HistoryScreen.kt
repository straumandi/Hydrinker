package com.example.hydrinker.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hydrinker.R
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
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun HistoryScreen(navController: NavController, context: Context = LocalContext.current) {
    val hydrationViewModel: HydrationViewModel =
        viewModel(factory = HydrationViewModelFactory(context))
    val hydrationDataList by hydrationViewModel.getLastWeekHydrationData()
        .observeAsState(initial = emptyList())

    val earliestDate = remember { mutableStateOf(LocalDate.now()) }
    val chartModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(key1 = hydrationDataList) {
        if (hydrationDataList.isNotEmpty()) {
            println("hydrationDataList: $hydrationDataList")
            earliestDate.value = hydrationDataList.minOf {
                it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            }
            val formattedEntries = getFormattedEntries(hydrationDataList, earliestDate.value)
            chartModelProducer.setEntries(formattedEntries)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFD3F7FF))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_history),
            contentDescription = "home_bg",
            modifier = Modifier.scale(1.8f).align(Alignment.TopStart).fillMaxSize()
        )
    }
    ScreenHeader(headerText = "History")
    Card(
        modifier = Modifier
            .padding(4.dp, 60.dp, 8.dp, 128.dp).shadow(elevation = 6.dp) ,// start - top - end - bottom

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD3F7FF),
        ),
    ) {
        Text("Check out your drinking behaviour! 💦",
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
            bottomAxis = rememberBottomAxis(valueFormatter = createDateFormatAxisValueFormatter(earliestDate = earliestDate.value)),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}


private fun getFormattedEntries(entries: List<HydrationData>, earliestDate: LocalDate): List<FloatEntry> {
    val accumulatedAmounts = mutableMapOf<LocalDate, Float>()

    entries.forEach { entry ->
        val parsedDate: LocalDate = entry.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        accumulatedAmounts[parsedDate] = (accumulatedAmounts[parsedDate] ?: 0f) + entry.amountInMillilitres.toFloat()
    }

    // Calculate the x value as the number of days from the earliest date
    return accumulatedAmounts.map { (parsedDate, accumulatedAmount) ->
        val daysSinceEarliest = ChronoUnit.DAYS.between(parsedDate, earliestDate).toFloat()
        entryOf(
            x = daysSinceEarliest,
            y = accumulatedAmount
        )
    }.sortedBy { it.x }
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

private fun createDateFormatAxisValueFormatter(earliestDate: LocalDate): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d.MM")

    return AxisValueFormatter { value, _ ->
        println("Input value for Axis: $value")

        val epochDay = value.toLong()
        val date = earliestDate.plusDays(value.toLong())

        println("epochDay $epochDay")
        println("date $date")

        println("Formatted date for Axis: ${date.format(dateTimeFormatter)}")
        date.format(dateTimeFormatter)
    }
}

