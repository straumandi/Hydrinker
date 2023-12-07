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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, context: Context = LocalContext.current) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }
    var dailyGoal by remember { mutableStateOf(TextFieldValue("")) }
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var drinkSize by remember { mutableStateOf(TextFieldValue("")) }

    var isNameValid by remember { mutableStateOf(true) }
    var isWeightValid by remember { mutableStateOf(true) }
    var isAgeValid by remember { mutableStateOf(true) }
    var isDailyGoalValid by remember { mutableStateOf(true) }
    var isDrinkSizeValid by remember { mutableStateOf(true) }

    fun validateFields(name: String, weight: String, age: String, dailyGoal: String, drinkSize: String): Boolean {
        isNameValid = name.isNotEmpty()
        isWeightValid = weight.isNotEmpty() && weight.toDoubleOrNull() != null
        isAgeValid = age.isNotEmpty() && age.toIntOrNull() != null
        isDailyGoalValid = dailyGoal.isNotEmpty() && dailyGoal.toDoubleOrNull() != null
        isDrinkSizeValid = drinkSize.isNotEmpty() && drinkSize.toDoubleOrNull() != null

        return isNameValid && isWeightValid && isAgeValid && isDailyGoalValid && isDrinkSizeValid
    }

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
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            isError = isNameValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight") },
            trailingIcon = { Text("kg", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = isWeightValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            singleLine = true,
            isError = isAgeValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = dailyGoal,
            onValueChange = { dailyGoal = it },
            label = { Text("Daily Goal") },
            trailingIcon = { Text("l", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = isDailyGoalValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = drinkSize,
            onValueChange = { drinkSize = it },
            label = { Text("Default Drink Size") },
            trailingIcon = { Text("l", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            isError = isDrinkSizeValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                val isValid = validateFields(name.text, weight.text, age.text, dailyGoal.text, drinkSize.text)
                if (isValid)
                 {
                    saveUser(
                        context,
                        name.text,
                        weight.text,
                        age.text,
                        dailyGoal.text,
                        drinkSize.text
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
