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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.Screens
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.ui.theme.HydrinkerTheme
@Composable
fun ScoreScreen(navController: NavController) {

    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        ScreenHeader(headerText = "Score")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 15.dp, end = 15.dp)
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            // Profile button at the top left

            // Score
            IconButton(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {/* TODO */}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_score),
                    contentDescription = "Big Blue Button",
                    modifier = Modifier
                        .size(200.dp)
                )
                Column {
                    Text(
                        text = "69",
                        modifier = Modifier.align(Alignment.End),
                        color = Color.Black,
                        fontSize = 84.sp,
                    )
                }
            }

            // Add a Spacer for vertical separation
            Spacer(modifier = Modifier.height(16.dp))

            // Streak
            IconButton(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {/* TODO */}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_streak),
                    contentDescription = "Big Blue Button",
                    modifier = Modifier
                        .size(200.dp)
                )
                Column {
                    Text(
                        text = "3x",
                        modifier = Modifier.align(Alignment.End),
                        color = Color.Black,
                        fontSize = 84.sp,
                    )
                }
            }

            // Add another Spacer for vertical separation
            Spacer(modifier = Modifier.height(16.dp))

            // Tier
            IconButton(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {/* TODO */}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_tier),
                    contentDescription = "Big Blue Button",
                    modifier = Modifier
                        .size(200.dp)
                )
                Column {
                    Text(
                        text = "River",
                        modifier = Modifier.align(Alignment.Start),
                        color = Color.Black,
                        fontSize = 42.sp,
                    )
                }
            }
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