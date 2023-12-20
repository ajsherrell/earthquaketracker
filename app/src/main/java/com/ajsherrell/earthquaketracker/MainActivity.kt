package com.ajsherrell.earthquaketracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajsherrell.earthquaketracker.navigation.Navigation
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
