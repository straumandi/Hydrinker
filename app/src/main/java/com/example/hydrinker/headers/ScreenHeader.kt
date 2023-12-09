package com.example.hydrinker.headers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenHeader(headerText: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(bottomEnd = 96.dp, bottomStart = 96.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = headerText,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}