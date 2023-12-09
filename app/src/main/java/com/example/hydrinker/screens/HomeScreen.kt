package com.example.hydrinker.screens

import android.webkit.WebSettings.TextSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        // Profile button at the top left
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
            shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp, bottomEnd = 50.dp, bottomStart = 50.dp),
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_profile),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Logo beneath the profile button
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.btn_home_score), contentDescription = null)
        // Insert your logo here

        // Add Cup button at the bottom left
        Spacer(modifier = Modifier.weight(1f))


        //Cup standard button
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
            shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp, bottomEnd = 50.dp, bottomStart = 50.dp),
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_addsrd),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxSize()
            )
        }

        //Cup custom button
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
            shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp, bottomEnd = 50.dp, bottomStart = 50.dp),
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_addcstm),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}
