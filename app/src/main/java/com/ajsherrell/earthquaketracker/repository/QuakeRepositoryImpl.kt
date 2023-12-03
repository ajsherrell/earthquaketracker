package com.ajsherrell.earthquaketracker.repository

import com.ajsherrell.earthquaketracker.api.QuakeData
import com.ajsherrell.earthquaketracker.api.UsgsApi
import com.ajsherrell.earthquaketracker.domain.QuakeRepository

class QuakeRepositoryImpl(
    private val api: UsgsApi
): QuakeRepository {

    override suspend fun getQuake(
        startTime: String,
        endTime: String,
        minMagnitude: Int
    ): retrofit2.Response<QuakeData> {
        return api.getQuake(
            startTime = startTime,
            endTime = endTime,
            minMagnitude = minMagnitude
        )
    }
}
