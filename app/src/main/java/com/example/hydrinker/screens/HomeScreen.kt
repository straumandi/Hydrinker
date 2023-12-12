package com.example.hydrinker.screens

import android.webkit.WebSettings.TextSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.navigation.compose.rememberNavController
import com.example.hydrinker.Screens
import com.example.hydrinker.ui.theme.HydrinkerTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

Row (
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
){
    IconButton(
        modifier = Modifier
            .size(120.dp, 120.dp),
        onClick = {}
    ) {
        Image(
            painter = painterResource(id = R.drawable.btn_home_profile),
            contentDescription = "Testing",
            modifier = Modifier.fillMaxWidth(),
        )
    }

    IconButton(
        modifier = Modifier
            .size(120.dp, 120.dp)
            .align(Alignment.CenterVertically),
        onClick = {}
    ) {
        Image(
            painter = painterResource(id = R.drawable.btn_home_menu),
            contentDescription = "Testing",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

        // Profile button at the top left


        // Logo beneath the profile button
        Spacer(modifier = Modifier.height(16.dp))

        IconButton(
            modifier = Modifier
                .size(300.dp, 300.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {},


        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_score),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxWidth()
            )
        }




        //Cup standard button
        IconButton(

            modifier = Modifier
                .size(120.dp, 120.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_addsrd),
                contentDescription = "Testing",
                modifier = Modifier.fillMaxWidth()
            )
        }

        //Cup custom button
        IconButton(
            modifier = Modifier.size(50.dp,50.dp),
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_home_addcstm),
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
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
