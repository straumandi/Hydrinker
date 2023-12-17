package com.example.hydrinker.screens

import ScoreService
import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Devices
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.Screens
import com.example.hydrinker.services.HydrationViewModel
import com.example.hydrinker.services.HydrationViewModelFactory
import com.example.hydrinker.headers.ScreenHeader
import com.example.hydrinker.ui.theme.HydrinkerTheme
import java.util.Date

@Composable
fun ScoreScreen(navController: NavController, context: Context = LocalContext.current) {
    val hydrationViewModel: HydrationViewModel =
        viewModel(factory = HydrationViewModelFactory(context))
    val scoreService = ScoreService(context, hydrationViewModel)
    var score by remember { mutableStateOf(0.0) }

    LaunchedEffect(key1 = Unit) {
        score = scoreService.calculateDailyScore(Date())
        println(score)
    }

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
                    contentDescription = "btn_score_score",
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
                    contentDescription = "btn_score_streak",
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
                    contentDescription = "btn_score_tier",
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