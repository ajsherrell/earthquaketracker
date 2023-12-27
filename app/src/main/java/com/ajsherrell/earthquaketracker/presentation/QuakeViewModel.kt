package com.ajsherrell.earthquaketracker.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajsherrell.earthquaketracker.api.QuakeData
import com.ajsherrell.earthquaketracker.domain.QuakeRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class QuakeViewModel(
    private val repository: QuakeRepository
): ViewModel() {
    private val _quakeTrackerData = MutableLiveData<QuakeData>()
    val quakeTrackerData: LiveData<QuakeData> = _quakeTrackerData

    var startTime = mutableStateOf("")
    var endTime = mutableStateOf("")
    var isEnabled = mutableStateOf(false)
    var isVisible = mutableStateOf(false)
    var showError = mutableStateOf(false)
    val regexToMatch = Regex("\\d{4}-\\d{2}-\\d{2}")
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate: LocalDate = LocalDate.now()
    val magnitudeOptions = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
    var expanded = mutableStateOf(false)
    var selectedMinMag = mutableStateOf("")

    suspend fun fetchQuakeData(startTime: String, endTime: String, minMagnitude: Int) {
        viewModelScope.launch {
            try {
                val data = repository.getQuake(
                    startTime,
                    endTime,
                    minMagnitude
                )
                _quakeTrackerData.value = data.body()
            } catch (e: Exception) {
                throw IllegalArgumentException(e.message)
            }
        }
    }
}
