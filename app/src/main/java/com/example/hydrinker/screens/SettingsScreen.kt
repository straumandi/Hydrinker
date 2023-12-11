package com.example.hydrinker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.models.MeasurementUnit

@Preview
@Composable
fun SettingsScreen(navController: NavController = NavController(LocalContext.current)) {
    var uiState by remember { mutableStateOf(SettingsUiState()) }

    ScreenHeader(headerText = "Settings")
    Column(
        modifier = Modifier
            .padding(top = 96.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        CustomStyledSwitch(
            checked = uiState.notificationsOn,
            onCheckedChange = {
                uiState = uiState.copy(notificationsOn = it)
            },
            text = "Notifications"
        )

        UnitDropDownMenu( onClick = {
            uiState = uiState.copy(units = it)
        })
    }
}

@Composable
fun CustomStyledSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(30.dp)
            .scale(1.10f)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 27.sp),
            modifier = Modifier
                .background(Color.Transparent)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .scale(1.10f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropDownMenu(onClick: (MeasurementUnit) -> Unit ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(MeasurementUnit.MILLILITERS) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MeasurementUnit.values().forEach { item ->
                if (item == MeasurementUnit.UNKNOWN) return@forEach
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        onClick(item)
                    }
                )
            }
        }
    }
}

data class SettingsUiState(
    val notificationsOn: Boolean = false,
    val units: MeasurementUnit = MeasurementUnit.UNKNOWN,
    val eMail: TextFieldValue = TextFieldValue(""),
    val areUnitsValid: Boolean = false,
    val isEmailValid: Boolean = true,
    val isLoading: Boolean = false
) {
    fun validateState(): SettingsUiState {

        return this.copy(
            areUnitsValid = false,
            isEmailValid = false,
        )
    }

    val isValid: Boolean
        get() = areUnitsValid && isEmailValid
}

data class SettingsData(
    val notificationsOn: Boolean,
    val units: MeasurementUnit,
    val eMail: String
)
