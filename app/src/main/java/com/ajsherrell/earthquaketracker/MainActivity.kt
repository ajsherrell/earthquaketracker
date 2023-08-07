package com.ajsherrell.earthquaketracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ajsherrell.earthquaketracker.api.QuakeData
import com.ajsherrell.earthquaketracker.ui.theme.EarthquakeTrackerTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<QuakeViewModel>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            val result = viewModel.quakeApi.getQuake()
            Log.d("Get the quake response: ", "${result.body()}")
            setContent {
                EarthquakeTrackerTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        result.body()?.let { Greeting(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(data: QuakeData, modifier: Modifier = Modifier) {
    Text(
        text = "Hello ${data.magnitude}!",
        modifier = modifier
    )
}
