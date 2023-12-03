package com.ajsherrell.earthquaketracker.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajsherrell.earthquaketracker.api.QuakeData
import com.ajsherrell.earthquaketracker.domain.QuakeRepository
import kotlinx.coroutines.launch

class QuakeViewModel(
    private val repository: QuakeRepository
): ViewModel() {

    private val defaultStartTime = "2023-01-01"
    private val defaultEndTime = "2023-12-31"
    private val defaultMinMagnitude = 5

    private val _quakeTrackerData = MutableLiveData<QuakeData>()
    val quakeTrackerData: LiveData<QuakeData> = _quakeTrackerData

    private var _startTime = mutableStateOf(defaultStartTime)
    val startTime: String
        get() = _startTime.value
    fun updateStartTime(input: String) {
        _startTime.value = input
    }

    private var _endTime = mutableStateOf(defaultEndTime)
    val endTime: String
        get() = _endTime.value
    fun updateEndTime(input: String) {
        _endTime.value = input
    }

    var minMagnitude by mutableStateOf(defaultMinMagnitude)
    suspend fun fetchQuakeData() {
        viewModelScope.launch {
            try {
                val data = repository.getQuake(startTime, endTime, defaultMinMagnitude)
                _quakeTrackerData.value = data.body()
            } catch (e: Exception) {
                throw IllegalArgumentException(e.message)
            }
        }
    }
}
