package com.ajsherrell.earthquaketracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ajsherrell.earthquaketracker.navigation.Navigation
import com.ajsherrell.earthquaketracker.navigation.Screen
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel
import com.ajsherrell.earthquaketracker.presentation.viewModelFactory
import com.ajsherrell.earthquaketracker.ui.theme.EarthquakeTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<QuakeViewModel>(
                factory = viewModelFactory {
                    QuakeViewModel(MyApp.appModule.quakeRepository)
                }
            )
            EarthquakeTrackerTheme {
                Navigation(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Get quake response here."
            )
            DividerLine()
            GetUserInput(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

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

@Composable
fun DividerLine() {
    Spacer(modifier = Modifier
        .height(1.dp)
        .background(Color.Gray))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetUserInput(
    modifier: Modifier,
    navController: NavController
) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    Column {
        TextField(
            value = startTime,
            onValueChange = { newStartTime ->
                startTime = newStartTime
            },
            modifier = modifier,
            label = { Text(text = "Start Date:") },
            singleLine = true,
            maxLines = 1,
            enabled = true,
            textStyle = TextStyle(Color.DarkGray),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DividerLine()
        TextField(
            value = endTime,
            onValueChange = { newEndTime ->
                endTime = newEndTime
            },
            modifier = modifier,
            label = { Text(text = "End Date:") },
            singleLine = true,
            maxLines = 1,
            enabled = true,
            textStyle = TextStyle(Color.DarkGray),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        DividerLine()
        Button(
            onClick = {
                navController.navigate(Screen.CountScreen.withArgs(
                    startTime,
                    endTime
                ))
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Enter Data",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
