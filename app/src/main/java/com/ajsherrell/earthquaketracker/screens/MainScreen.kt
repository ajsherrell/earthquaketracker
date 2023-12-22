package com.ajsherrell.earthquaketracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ajsherrell.earthquaketracker.DividerLine
import com.ajsherrell.earthquaketracker.navigation.Screen
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://images.unsplash.com/photo-1603869311144-66b03d340b32?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZWFydGhxdWFrZXxlbnwwfHwwfHx8MA%3D%3D")
            .build()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Earthquakes") },
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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .paint(painter = painter, contentScale = ContentScale.FillBounds)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GetUserInput(
    modifier: Modifier,
    navController: NavController
) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var isEnabled by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val regexToMatch = Regex("\\d{4}-\\d{2}-\\d{2}")
    val currentDate = LocalDate.now()

    Column {
        TextField(
            value = startTime,
            onValueChange = { newStartTime ->
                startTime = newStartTime
            },
            placeholder = {
                Text(
                    text = "YYYY-MM-DD",
                    color = Color.LightGray
                )
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
                if (startTime.matches(regexToMatch) &&
                    endTime.matches(regexToMatch)) {
                    isEnabled = true
                    showError = false
                } else showError = true
            },
            placeholder = {
                Text(
                    text = "YYYY-MM-DD",
                    color = Color.LightGray
                )
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
                if (currentDate.isAfter(LocalDate.parse(endTime)) &&
                    LocalDate.parse(endTime).isAfter(LocalDate.parse(startTime))) {
                    navController.navigate(
                        Screen.CountScreen.withArgs(
                            startTime,
                            endTime
                        ))
                    isVisible = true
                    showError = false
                } else {
                    showError = true
                }

            },
            enabled = isEnabled,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Enter Data",
                modifier = Modifier.padding(8.dp)
            )
        }
        if (isVisible) {
            Text(text = "Loading...")
        }
        if (showError) {
            Text(
                text = "Please enter correct format: yyyy-mm-dd.\nEnter $currentDate or less.",
                color = Color.Red
            )
        }
    }
}
