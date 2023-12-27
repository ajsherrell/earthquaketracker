package com.ajsherrell.earthquaketracker.presentation

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
    private val _quakeTrackerData = MutableLiveData<QuakeData>()
    val quakeTrackerData: LiveData<QuakeData> = _quakeTrackerData

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
