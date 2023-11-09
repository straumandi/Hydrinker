package com.example.hydrinker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hydrinker.ui.theme.HydrinkerTheme

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        // Profile button at the top left
        IconButton(
            onClick = { /* Go to Profile page */ },
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Logo beneath the profile button
        Spacer(modifier = Modifier.height(16.dp))
        // Insert your logo here

        // Add Cup button at the bottom left
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { /* Open cup settings */ },
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "Add standard cup",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Score button at the bottom right
        IconButton(
            onClick = { /* Go to Score page */ },
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "Score",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
