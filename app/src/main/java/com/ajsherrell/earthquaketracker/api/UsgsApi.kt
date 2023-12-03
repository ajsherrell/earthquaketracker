package com.ajsherrell.earthquaketracker.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsgsApi {
    @GET("query?format=geojson")
    suspend fun getQuake(
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String,
        @Query("minmagnitude") minMagnitude: Int
    ) : Response<QuakeData>
}
