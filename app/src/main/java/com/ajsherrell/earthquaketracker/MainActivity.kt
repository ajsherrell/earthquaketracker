package com.ajsherrell.earthquaketracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel
import com.ajsherrell.earthquaketracker.presentation.viewModelFactory
import com.ajsherrell.earthquaketracker.ui.theme.EarthquakeTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EarthquakeTrackerTheme {
                val viewModel = viewModel<QuakeViewModel>(
                    factory = viewModelFactory {
                        QuakeViewModel(MyApp.appModule.quakeRepository)
                    }
                )
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Get quake response here."
                        )
                        DividerLine()
                        Greeting(viewModel = viewModel)
                        DividerLine()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: QuakeViewModel, modifier: Modifier = Modifier) {
    val data by viewModel.quakeTrackerData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchQuakeData()
    }
    Column {
        if (data?.features?.isEmpty() == true) {
            Text(text = "Loading...")
        } else {
            Text(
                text = "Earth quake count is ${data?.metadata?.count}.",
                modifier = modifier.padding(8.dp)
            )
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
fun UpdateUserInput(viewModel: QuakeViewModel, modifier: Modifier = Modifier) {
    Column {
        TextField(
            value = viewModel.startTime,
            onValueChange = { startTime ->
                viewModel.updateStartTime(startTime)
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
            value = viewModel.endTime,
            onValueChange = { endTime ->
                viewModel.updateEndTime(endTime)
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
            onClick = {},
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Enter Data",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

