package com.ajsherrell.earthquaketracker.api

import com.ajsherrell.earthquaketracker.USGS_REQUEST_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

object RetrofitHelper {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USGS_REQUEST_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}