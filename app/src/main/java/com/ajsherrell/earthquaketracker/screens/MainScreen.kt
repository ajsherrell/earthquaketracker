package com.ajsherrell.earthquaketracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
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
import com.ajsherrell.earthquaketracker.presentation.QuakeViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: QuakeViewModel) {
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
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GetUserInput(
    viewModel: QuakeViewModel,
    modifier: Modifier,
    navController: NavController
) {
    Column {
        TextField(
            value = viewModel.startTime.value,
            onValueChange = { newStartTime ->
                viewModel.startTime.value = newStartTime
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
            value = viewModel.endTime.value,
            onValueChange = { newEndTime ->
                viewModel.endTime.value = newEndTime
                if (viewModel.startTime.value.matches(viewModel.regexToMatch) &&
                    viewModel.endTime.value.matches(viewModel.regexToMatch)
                ) {
                    viewModel.isEnabled.value = true
                    viewModel.showError.value = false
                } else viewModel.showError.value = true
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
        ExposedDropdownMenuBox(
            expanded = viewModel.expanded.value,
            onExpandedChange = { viewModel.expanded.value = !viewModel.expanded.value }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = viewModel.selectedMinMag.value,
                onValueChange = {},
                label = { Text("Minimum Magnitude:") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.expanded.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(expanded = viewModel.expanded.value, onDismissRequest = { viewModel.expanded.value = false }) {
                viewModel.magnitudeOptions.forEach { selectedOption ->
                    DropdownMenuItem(
                        text = { Text(selectedOption) },
                        onClick = {
                            viewModel.selectedMinMag.value = selectedOption
                            viewModel.expanded.value = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        DividerLine()
        Button(
            onClick = {
                if (viewModel.currentDate.isAfter(LocalDate.parse(viewModel.endTime.value)) &&
                    LocalDate.parse(viewModel.endTime.value).isAfter(LocalDate.parse(viewModel.startTime.value))) {
                    navController.navigate(
                        Screen.CountScreen.withArgs(
                            viewModel.startTime.value,
                            viewModel.endTime.value,
                            viewModel.selectedMinMag.value
                        ))
                    viewModel.isVisible.value = true
                    viewModel.showError.value = false
                } else {
                    viewModel.showError.value = true
                }

            },
            enabled = viewModel.isEnabled.value,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Enter Data",
                modifier = Modifier.padding(8.dp)
            )
        }
        if (viewModel.isVisible.value) {
            Text(text = "Loading...")
        }
        if (viewModel.showError.value) {
            Text(
                text = "Please enter correct format: yyyy-mm-dd.\nEnter $viewModel.currentDate or less.",
                color = Color.Red
            )
        }
    }
}
