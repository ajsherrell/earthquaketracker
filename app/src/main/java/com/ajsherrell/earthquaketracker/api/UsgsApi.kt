package com.ajsherrell.earthquaketracker.api

import com.ajsherrell.earthquaketracker.USGS_REQUEST_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface UsgsApi {
    @GET("query?format=geojson&starttime=2022-01-01&endtime=2023-01-01&minmagnitude=5")
    suspend fun getQuake() : Response<QuakeData>
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