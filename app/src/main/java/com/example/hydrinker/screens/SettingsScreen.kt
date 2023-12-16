package com.example.hydrinker.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.models.MeasurementUnit
import com.example.hydrinker.services.HydrationViewModel
import com.example.hydrinker.services.HydrationViewModelFactory
import com.example.hydrinker.services.SettingsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SettingsScreen(
    navController: NavController,
    context: Context = LocalContext.current
) {
    val hydrationViewModel: HydrationViewModel = viewModel(factory = HydrationViewModelFactory(context))

    var uiState by remember { mutableStateOf(SettingsUiState()) }
    val settingsService = SettingsService(context.dataStore, context)

    LaunchedEffect(key1 = Unit) {
        val existingProfile = settingsService.readSettings()
        uiState = SettingsUiState(
            notificationsOn = existingProfile.notificationsOn,
            units = existingProfile.units
        )
    }

    ScreenHeader(headerText = "Settings")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 15.dp, end = 15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Small button for seeding data
                Button(
                    onClick = { hydrationViewModel.seedDataForPastWeek() },
                    modifier = Modifier
                        .size(width = 80.dp, height = 32.dp)
                        .padding(top = 4.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFE0E0E0))
                ) {
                    Text("Seed", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomStyledSwitch(
                checked = uiState.notificationsOn,
                onCheckedChange = {
                    uiState = uiState.copy(notificationsOn = it)
                },
                text = "Notifications"
            )

            CustomStyledSwitch(
                checked = uiState.units == MeasurementUnit.METRIC,
                onCheckedChange = {
                    uiState =
                        uiState.copy(units = if (it) MeasurementUnit.METRIC else MeasurementUnit.IMPERIAL)
                },
                text = uiState.units.name
            )

            Spacer(modifier = Modifier.weight(1f)) // This will push everything below to the bottom

            Button(
                onClick = {
                    uiState = uiState.copy(isLoading = true)
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsService.saveSettings(
                            SettingsData(
                                notificationsOn = uiState.notificationsOn,
                                units = uiState.units
                            )
                        )
                        delay(1500)
                        uiState = uiState.copy(isLoading = false)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Settings saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Save Settings")
                }
            }

        }
    }
}

@Composable
fun CustomStyledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(30.dp)
            .width(200.dp)
            .scale(1.10f),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 27.sp),
            modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .scale(1.10f)
        )
    }
}

data class SettingsUiState(
    val notificationsOn: Boolean = false,
    val units: MeasurementUnit = MeasurementUnit.METRIC,
    val eMail: TextFieldValue = TextFieldValue(""),
    val areUnitsValid: Boolean = false,
    val isEmailValid: Boolean = true,
    val isLoading: Boolean = false
)

data class SettingsData(
    val notificationsOn: Boolean,
    val units: MeasurementUnit,
)
