package com.example.hydrinker.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.hydrinker.R
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.services.HydrationViewModel
import com.example.hydrinker.services.HydrationViewModelFactory
import com.example.hydrinker.services.ProfileService

@Composable
fun HomeScreen(
    navController: NavController,
    context: Context = LocalContext.current
) {
    var showDialog by remember { mutableStateOf(false) }
    var defaultDrinkSize by remember { mutableStateOf("") }
    val profileService = ProfileService(context.dataStore)

    val hydrationViewModel: HydrationViewModel =
        viewModel(factory = HydrationViewModelFactory(context))

    LaunchedEffect(key1 = Unit) {
        defaultDrinkSize = profileService.readProfile().drinkSize.toString()
    }

    if (showDialog) {
        DrinkInputDialog(onDismissRequest = { showDialog = false }, onConfirm = { size ->
            hydrationViewModel.addDrink(size)
            showDialog = false
        })
    }
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        ScreenHeader(headerText = "Hydrinker")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            // Profile Button top left
            IconButton(modifier = Modifier.size(120.dp, 120.dp), onClick = {
                navController.navigate("profile_route") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }) {
                Image(
                    painter = painterResource(id = R.drawable.btn_home_profile),
                    contentDescription = "A profile button",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            // Menu Button top right
            IconButton(modifier = Modifier
                .size(120.dp, 120.dp)
                .align(Alignment.CenterVertically),
                onClick = {
                    navController.navigate("settings_route") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },) {
                Image(
                    painter = painterResource(id = R.drawable.btn_home_menu),
                    contentDescription = "A menu button",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        // Logo beneath the profile button
        Spacer(modifier = Modifier.height(16.dp))

        // Big Score Button in the middle of screen
        IconButton(
            modifier = Modifier
                .size(300.dp, 300.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate("score_route") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_score),
                contentDescription = "Big Blue Button",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column {
                Text(
                    text = "Score",
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.Black,
                    fontSize = 42.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Text(
                    text = "69",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.Black,
                    fontSize = 84.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            }
        }


        //Cup standard button bottom middle
        Row {
            IconButton(modifier = Modifier
                .size(120.dp, 120.dp),
                onClick = {
                    if (!isDrinkSizeInvalid(defaultDrinkSize)) {
                        hydrationViewModel.addDrink(defaultDrinkSize.toInt())
                    } else {
                        showDialog = true
                    }
                }) {
                Image(
                    painter = painterResource(id = R.drawable.btn_home_addsrd),
                    contentDescription = "Standard drink size button",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(0.dp))
            //Cup custom button bottom middle
            IconButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                onClick = {
                    showDialog = true
                }) {
                Image(
                    painter = painterResource(id = R.drawable.btn_home_addcstm),
                    contentDescription = "Plus Button",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


    }
}

@Composable
fun DrinkInputDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var drinkSize by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(onDismissRequest = { onDismissRequest() },
        title = { Text("Enter Drink Size") },
        text = {
            Column {
                OutlinedTextField(
                    value = drinkSize,
                    onValueChange = {
                        drinkSize = it
                        showError = isDrinkSizeInvalid(it)
                    },
                    isError = showError,
                    label = { Text("Drink Size") },
                    placeholder = { Text("e.g. 250") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                if (showError) {
                    Text("Invalid input. Please enter a number.", color = Color.Red)
                }
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!isDrinkSizeInvalid(drinkSize)) {
                        onConfirm(drinkSize.toInt())
                    }
                },
            ) {
                Text("Confirm")
            }
        })
}


fun isDrinkSizeInvalid(drinkSize: String): Boolean {
    return try {
        drinkSize.toInt()
        false
    } catch (e: NumberFormatException) {
        true
    }
}

