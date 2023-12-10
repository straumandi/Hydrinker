package com.example.hydrinker.screens
import android.webkit.WebSettings.TextSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.hydrinker.R

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color

import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.Screens
import com.example.hydrinker.ui.theme.HydrinkerTheme
@Composable
fun ScoreScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Button(
            modifier = Modifier.size(80.dp,80.dp),
            border = BorderStroke(1.dp, color = Color(0xFF000000)),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 30.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF98E0FF),
                contentColor = Color(0xFF000000),
            ),
            shape = CircleShape,
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_profile),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xFFbddbe3,
    device = Devices.DEFAULT,
    widthDp = 360,
    heightDp = 640
)
fun ScoreScreenPreview() {
    val navController = rememberNavController()
    ScoreScreen(navController)
}