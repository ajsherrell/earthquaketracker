package com.ajsherrell.earthquaketracker.domain

import com.ajsherrell.earthquaketracker.api.QuakeData
import retrofit2.Response

interface QuakeRepository {
    suspend fun getQuake(
        startTime: String,
        endTime: String,
        minMagnitude: Int
    ): Response<QuakeData>
}
