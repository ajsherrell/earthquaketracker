package com.ajsherrell.earthquaketracker.screens

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ajsherrell.earthquaketracker.api.Feature
import com.ajsherrell.earthquaketracker.api.QuakeData
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
                modifier = modifier,
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
        Column(modifier = Modifier.padding(padding)) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(top = 10.dp, bottom = 10.dp))
            ) {
                if (data == null) {
                    Text(
                        text = "Loading...",
                        color = colorState.value
                    )
                } else {
                    Text(
                        text = "Earthquake count is ${data?.metadata?.count}.",
                        modifier = modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = Color.Gray
            )
            SetSearchBar(data)
//            ListOfQuakePlaces(data)
        }
    }
}

@Composable
private fun ListOfQuakePlaces(data: QuakeData?,searchQuery: String) {
    var places by mutableStateOf<List<Feature>>(emptyList())

    try {
        val fetchedPlaces = data?.features
        if (fetchedPlaces != null) {
            places = fetchedPlaces
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(if (searchQuery.isBlank()) places else places.filter {
            it.properties.place?.contains(searchQuery, ignoreCase = true) ?: false }) { place ->
            ItemRow(place.properties.place)
            HorizontalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun ItemRow(place: String?) {
    if (place != null) {
        Text(
            text = place,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color.Black,
            fontWeight = FontWeight.Thin
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SetSearchBar(data: QuakeData?) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .semantics { traversalIndex = -1f },
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text(text = "search location") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search for locations.") },
            trailingIcon = { Icon(Icons.Default.Clear, contentDescription = "Clear search text.") }
        ) {
            ListOfQuakePlaces(data = data, searchQuery)
        }
    }
}
