package com.ajsherrell.earthquaketracker.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    endTime: String
) {
    LaunchedEffect(Unit) {
        viewModel.fetchQuakeData(startTime, endTime)
    }
    val data by viewModel.quakeTrackerData.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quake Count") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Navigate Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (data == null) {
                Text(text = "Loading...")
            } else {
                Text(
                    text = "Earth quake count is ${data?.metadata?.count}.",
                    modifier = modifier.padding(8.dp)
                )
            }
        }
    }
}
