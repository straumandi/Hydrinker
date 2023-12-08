package com.example.hydrinker.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.example.hydrinker.services.ProfileService
import com.example.hydrinker.validators.ProfileValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

@Composable
fun ProfileScreen(navController: NavController, context: Context = LocalContext.current) {
    var uiState by remember { mutableStateOf(ProfileUiState()) }
    val profileService = ProfileService(context.dataStore)

    var nameFocused by remember { mutableStateOf(false) }
    var weightFocused by remember { mutableStateOf(false) }
    var ageFocused by remember { mutableStateOf(false) }
    var dailyGoalFocused by remember { mutableStateOf(false) }
    var defaultSizeFocused by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        val existingProfile = profileService.readProfile()
        uiState = ProfileUiState(
            name = TextFieldValue(existingProfile.name),
            weight = TextFieldValue(existingProfile.weight.toString()),
            age = TextFieldValue(existingProfile.age.toString()),
            dailyGoal = TextFieldValue(existingProfile.dailyGoal.toString()),
            drinkSize = TextFieldValue(existingProfile.drinkSize.toString())
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { uiState = uiState.copy(name = it) },
            label = { Text("Name") },
            singleLine = true,
            isError = !uiState.isNameValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (nameFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState =
                            uiState.copy(isNameValid = ProfileValidator.validateName(uiState.name.text))
                    }
                    nameFocused = focusState.isFocused
                },
            supportingText = {
                if (!uiState.isNameValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Name cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
        OutlinedTextField(
            value = uiState.weight,
            onValueChange = { uiState = uiState.copy(weight = it) }, label = { Text("Weight") },
            trailingIcon = { Text("kg", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = !uiState.isWeightValid,
            supportingText = {
                if (!uiState.isWeightValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        // TODO: REPLACE WITH PREFFERED WEIGHT SYSTEM
                        text = "Enter your weight in kg",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (weightFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState =
                            uiState.copy(isWeightValid = ProfileValidator.validateWeight(uiState.weight.text))
                    }
                    weightFocused = focusState.isFocused
                },
        )
        OutlinedTextField(
            value = uiState.age,
            onValueChange = { uiState = uiState.copy(age = it) }, label = { Text("Age") },
            singleLine = true,
            isError = !uiState.isAgeValid,
            supportingText = {
                if (!uiState.isAgeValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Please enter a valid age",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (ageFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState =
                            uiState.copy(isAgeValid = ProfileValidator.validateAge(uiState.age.text))
                    }
                    ageFocused = focusState.isFocused
                },
        )
        OutlinedTextField(
            value = uiState.dailyGoal,
            onValueChange = { uiState = uiState.copy(dailyGoal = it) },
            label = { Text("Daily Goal") },
            trailingIcon = { Text("l", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = !uiState.isDailyGoalValid,
            supportingText = {
                if (!uiState.isDailyGoalValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter your daily goal in liters",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (dailyGoalFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState = uiState.copy(
                            isDailyGoalValid = ProfileValidator.validateDailyGoal(uiState.dailyGoal.text)
                        )
                    }
                    dailyGoalFocused = focusState.isFocused
                },
        )
        OutlinedTextField(
            value = uiState.drinkSize,
            onValueChange = { uiState = uiState.copy(drinkSize = it) },
            label = { Text("Default Drink Size") },
            trailingIcon = { Text("l", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = !uiState.isDrinkSizeValid,
            supportingText = {
                if (!uiState.isDrinkSizeValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter your preferred drink size in liters",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged { focusState ->
                    if (defaultSizeFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState = uiState.copy(
                            isDrinkSizeValid = ProfileValidator.validateDrinkSize(uiState.drinkSize.text)
                        )
                    }
                    defaultSizeFocused = focusState.isFocused
                },
        )
        Button(
            onClick = {
                uiState = uiState.validateState()
                if (uiState.isValid) {
                    uiState = uiState.copy(isLoading = true)
                    CoroutineScope(Dispatchers.IO).launch {
                        profileService.saveProfile(
                            ProfileData(
                                name = uiState.name.text,
                                weight = uiState.weight.text.toDouble(),
                                age = uiState.age.text.toInt(),
                                dailyGoal = uiState.dailyGoal.text.toDouble(),
                                drinkSize = uiState.drinkSize.text.toDouble()
                            )
                        )
                        delay(3000) // testing purposes
                        uiState = uiState.copy(isLoading = false)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Profile saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please fill out the form correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Save Profile")
            }
        }
    }
}

data class ProfileUiState(
    val name: TextFieldValue = TextFieldValue(""),
    val weight: TextFieldValue = TextFieldValue(""),
    val age: TextFieldValue = TextFieldValue(""),
    val dailyGoal: TextFieldValue = TextFieldValue(""),
    val drinkSize: TextFieldValue = TextFieldValue(""),
    val isNameValid: Boolean = true,
    val isWeightValid: Boolean = true,
    val isAgeValid: Boolean = true,
    val isDailyGoalValid: Boolean = true,
    val isDrinkSizeValid: Boolean = true,
    val isLoading: Boolean = false
) {
    fun validateState(): ProfileUiState {
        return this.copy(
            isNameValid = ProfileValidator.validateName(name.text),
            isWeightValid = ProfileValidator.validateWeight(weight.text),
            isAgeValid = ProfileValidator.validateAge(age.text),
            isDailyGoalValid = ProfileValidator.validateDailyGoal(dailyGoal.text),
            isDrinkSizeValid = ProfileValidator.validateDrinkSize(drinkSize.text)
        )
    }

    val isValid: Boolean
        get() = isNameValid && isWeightValid && isAgeValid && isDailyGoalValid && isDrinkSizeValid
}

data class ProfileData(
    val name: String,
    val weight: Double,
    val age: Int,
    val dailyGoal: Double,
    val drinkSize: Double
)

