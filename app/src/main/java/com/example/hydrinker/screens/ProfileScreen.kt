package com.example.hydrinker.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.TextFieldDefaults.shape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.R
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.services.ProfileService
import com.example.hydrinker.validators.ProfileValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, context: Context = LocalContext.current) {
    // 'remember' is a Compose function to retain state across recompositions
    var uiState by remember { mutableStateOf(ProfileUiState()) }
    val profileService = ProfileService(context.dataStore)

    var nameFocused by remember { mutableStateOf(false) }
    var weightFocused by remember { mutableStateOf(false) }
    var ageFocused by remember { mutableStateOf(false) }
    var dailyGoalFocused by remember { mutableStateOf(false) }
    var defaultSizeFocused by remember { mutableStateOf(false) }

    // LaunchedEffect is a Compose function that runs a side-effect once
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

    // These are various composables that are used to build the screen
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

    ScreenHeader(headerText = "Profile")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Textfield: Name
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { uiState = uiState.copy(name = it) },
            label = { Text("Name") },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF84D0FB),
                unfocusedBorderColor = Color(0xFF004FAB),
            ),
            shape = RoundedCornerShape(25.dp),
            isError = !uiState.isNameValid,

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp).background(color = Color(0xFFD3F7FF).copy(alpha = 0.9f))
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
        // Textfield: Weight
        OutlinedTextField(
            value = uiState.weight,
            onValueChange = { uiState = uiState.copy(weight = it) }, label = { Text("Weight") },
            trailingIcon = { Text("kg", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF84D0FB),
                unfocusedBorderColor = Color(0xFF004FAB)
            ),
            shape = RoundedCornerShape(25.dp),
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
                .padding(vertical = 8.dp).background(color = Color(0xFFD3F7FF).copy(alpha = 0.9f))
                .onFocusChanged { focusState ->
                    if (weightFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState =
                            uiState.copy(isWeightValid = ProfileValidator.validateWeight(uiState.weight.text))
                    }
                    weightFocused = focusState.isFocused
                },
        )
        // Textfield: Age
        OutlinedTextField(
            value = uiState.age,
            onValueChange = { uiState = uiState.copy(age = it) }, label = { Text("Age") },
            singleLine = true,
            isError = !uiState.isAgeValid,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF84D0FB),
                unfocusedBorderColor = Color(0xFF004FAB)
            ),
            shape = RoundedCornerShape(25.dp),
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
                .padding(vertical = 8.dp).background(color = Color(0xFFD3F7FF).copy(alpha = 0.9f))
                .onFocusChanged { focusState ->
                    if (ageFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState =
                            uiState.copy(isAgeValid = ProfileValidator.validateAge(uiState.age.text))
                    }
                    ageFocused = focusState.isFocused
                },
        )
        // Textfield: Daily Goal
        OutlinedTextField(
            value = uiState.dailyGoal,
            onValueChange = { uiState = uiState.copy(dailyGoal = it) },
            label = { Text("Daily Goal") },
            trailingIcon = { Text("ml", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF84D0FB),
                unfocusedBorderColor = Color(0xFF004FAB)
            ),
            shape = RoundedCornerShape(25.dp),
            isError = !uiState.isDailyGoalValid,
            supportingText = {
                if (!uiState.isDailyGoalValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter your daily goal in milliliters",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp).background(color = Color(0xFFD3F7FF).copy(alpha = 0.9f))
                .onFocusChanged { focusState ->
                    if (dailyGoalFocused && !focusState.isFocused && !focusState.isFocused) {
                        uiState = uiState.copy(
                            isDailyGoalValid = ProfileValidator.validateDailyGoal(uiState.dailyGoal.text)
                        )
                    }
                    dailyGoalFocused = focusState.isFocused
                },
        )
        // Textfield: Default Drink Size
        OutlinedTextField(
            value = uiState.drinkSize,
            onValueChange = { uiState = uiState.copy(drinkSize = it) },
            label = { Text("Default Drink Size") },
            trailingIcon = { Text("ml", style = MaterialTheme.typography.bodySmall) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF84D0FB),
                unfocusedBorderColor = Color(0xFF004FAB)
            ),
            shape = RoundedCornerShape(25.dp),
            isError = !uiState.isDrinkSizeValid,
            supportingText = {
                if (!uiState.isDrinkSizeValid) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Enter your preferred drink size in milliliters",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp).background(color = Color(0xFFD3F7FF).copy(alpha = 0.9f))
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
                if (!uiState.isValid) {
                    Toast.makeText(
                        context,
                        "Please fill out the form correctly",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    uiState = uiState.copy(isLoading = true)
                    CoroutineScope(Dispatchers.IO).launch {
                        profileService.saveProfile(
                            ProfileData(
                                name = uiState.name.text,
                                weight = uiState.weight.text.toDouble(),
                                age = uiState.age.text.toInt(),
                                dailyGoal = uiState.dailyGoal.text.toInt(),
                                drinkSize = uiState.drinkSize.text.toInt()
                            )
                        )
                        delay(1500) // delay for testing purposes
                        uiState = uiState.copy(isLoading = false)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Profile saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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
                Text("Save Profile")
            }
        }
    }

}

// Data classes are a Kotlin feature providing a concise way to create classes that just hold data
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

        // 'copy' is a special function provided by Kotlin data classes to create a new instance with modified properties
        return this.copy(
            isNameValid = ProfileValidator.validateName(name.text),
            isWeightValid = ProfileValidator.validateWeight(weight.text),
            isAgeValid = ProfileValidator.validateAge(age.text),
            isDailyGoalValid = ProfileValidator.validateDailyGoal(dailyGoal.text),
            isDrinkSizeValid = ProfileValidator.validateDrinkSize(drinkSize.text)
        )
    }

    // Custom getter in Kotlin to compute a value directly in the property declaration
    val isValid: Boolean
        get() = isNameValid && isWeightValid && isAgeValid && isDailyGoalValid && isDrinkSizeValid
}

data class ProfileData(
    val name: String,
    val weight: Double,
    val age: Int,
    val dailyGoal: Int,
    val drinkSize: Int
)


@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFFbddbe3,
    device = Devices.DEFAULT,
    widthDp = 360,
    heightDp = 640
)
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}