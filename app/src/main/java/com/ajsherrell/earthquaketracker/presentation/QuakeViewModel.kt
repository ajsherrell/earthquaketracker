package com.ajsherrell.earthquaketracker.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

    private val defaultMinMagnitude = 5
    private var defaultStartTime = ""
    private var defaultEndTime = ""

    fun updateTimes(start: String, end: String) {
        defaultStartTime = start
        defaultEndTime = end
    }

    private val _quakeTrackerData = MutableLiveData<QuakeData>()
    val quakeTrackerData: LiveData<QuakeData> = _quakeTrackerData

    var minMagnitude by mutableIntStateOf(defaultMinMagnitude) // todo: create radio button.
    suspend fun fetchQuakeData() {
        viewModelScope.launch {
            try {
                val data = repository.getQuake(
                    defaultStartTime,
                    defaultEndTime,
                    defaultMinMagnitude
                )
                _quakeTrackerData.value = data.body()
            } catch (e: Exception) {
                throw IllegalArgumentException(e.message)
            }
        }
    }
}
