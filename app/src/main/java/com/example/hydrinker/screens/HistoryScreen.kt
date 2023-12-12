package com.example.hydrinker.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hydrinker.models.HydrationData
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
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
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@Composable
fun HistoryScreen(navController: NavController, entries: List<HydrationData>) {

    val chartModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(entries) {
        // update ChartEntryModelProducer when entries change
        chartModelProducer.setEntries(getFormattedEntries(entries))
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

// Helper function to format the entries for ChartEntryModelProducer
private fun getFormattedEntries(entries: List<HydrationData>): List<FloatEntry> {
    val referenceDate = entries.minOfOrNull { it.date.time } ?: 0L
    print("referenceDate: $referenceDate")
    return entries.map { entry ->
        entryOf(
            x = TimeUnit.MILLISECONDS.toDays(entry.date.time - referenceDate).toFloat(), // Convert to days
            y = entry.amount.toFloat(), // Y-axis represents the water amount
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






