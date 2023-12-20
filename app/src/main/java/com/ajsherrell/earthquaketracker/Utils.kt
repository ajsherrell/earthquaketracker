package com.ajsherrell.earthquaketracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine() {
    Spacer(modifier = Modifier
        .height(1.dp)
        .background(Color.Gray))
}