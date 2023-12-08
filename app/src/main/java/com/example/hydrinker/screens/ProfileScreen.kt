package com.example.hydrinker.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hydrinker.validators.ProfileValidator

@Composable
fun ProfileScreen(navController: NavController, context: Context = LocalContext.current) {
    var uiState by remember { mutableStateOf(ProfileUiState()) }

    fun saveUser(
        context: Context,
        name: String,
        weight: String,
        age: String,
        dailyGoal: String,
        drinkSize: String
    ) {
        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("Name", name)
            putString("Weight", weight)
            putString("Age", age)
            putString("DailyGoal", dailyGoal)
            putString("DrinkSize", drinkSize)
            apply()
        }
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
            onValueChange = { uiState = uiState.copy(name = it) },            label = { Text("Name") },
            singleLine = true,
            isError = !uiState.isNameValid,
            supportingText = {
                if (!uiState.isNameValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Name cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.weight,
            onValueChange = { uiState = uiState.copy(weight = it) },            label = { Text("Weight") },
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
        )
        OutlinedTextField(
            value = uiState.age,
            onValueChange = { uiState = uiState.copy(age = it) },            label = { Text("Age") },
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
        )
        OutlinedTextField(
            value = uiState.dailyGoal,
            onValueChange = { uiState = uiState.copy(dailyGoal = it) },            label = { Text("Daily Goal") },
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
        )
        OutlinedTextField(
            value = uiState.drinkSize,
            onValueChange = { uiState = uiState.copy(drinkSize = it) },            label = { Text("Default Drink Size") },
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
        )
        Button(
            onClick = {
                uiState = uiState.validate()
                if (uiState.isValid)
                 {
                    saveUser(
                        context,
                        uiState.name.text,
                        uiState.weight.text,
                        uiState.age.text,
                        uiState.dailyGoal.text,
                        uiState.drinkSize.text
                    )
                    Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong while saving the user",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Save Profile")
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
    val isDrinkSizeValid: Boolean = true
) {
    fun validate(): ProfileUiState {
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

