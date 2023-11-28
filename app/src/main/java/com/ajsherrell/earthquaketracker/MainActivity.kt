package com.ajsherrell.earthquaketracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            runOnUiThread {
                val quakeData = result.body()
                setContent {
                    EarthquakeTrackerTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "Get quake response here."
                                )
                                DividerLine()
                                if (quakeData != null) {
                                    Greeting(quakeData)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(data: QuakeData, modifier: Modifier = Modifier) {
    Text(
        text = "Hello ${data}!",
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun DividerLine() {
    Spacer(modifier = Modifier.height(1.dp).background(Color.Gray))
}
