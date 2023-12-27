package com.ajsherrell.earthquaketracker.screens

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: QuakeViewModel,
    startTime: String,
    endTime: String,
    minMagnitude: String
) {
    val colorState = remember { Animatable(Color.DarkGray) }
    val targetColor = Color.Gray
    LaunchedEffect(colorState) {
        while (true) {
            colorState.animateTo(targetColor, animationSpec = infiniteRepeatable(
                tween(durationMillis = 1000, easing = LinearEasing)
            ))
            colorState.animateTo(Color.DarkGray, animationSpec = infiniteRepeatable(
                tween(durationMillis = 1000, easing = LinearEasing)
            ))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchQuakeData(startTime, endTime, minMagnitude.toInt())
    }
    val data by viewModel.quakeTrackerData.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.border(1.dp, Color.DarkGray),
                title = { Text("Quake Count") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            viewModel.isVisible.value = false
                            viewModel.enterIsClicked.value = false
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color.LightGray,
                    scrolledContainerColor = Color.LightGray,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            if (data == null) {
                Text(
                    text = "Loading...",
                    color = colorState.value
                )
            } else {
                Text(
                    text = "Earth quake count is ${data?.metadata?.count}.",
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
}
