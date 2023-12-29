package com.ajsherrell.earthquaketracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DividerLine(height: Dp, color: Color) {
    Spacer(modifier = Modifier
        .background(color)
        .height(height)
    )
} //todo: Do I need this?