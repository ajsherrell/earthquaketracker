package com.ajsherrell.earthquaketracker.api

import com.ajsherrell.earthquaketracker.USGS_REQUEST_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UsgsApi {
    @GET("query")
    suspend fun getQuake() : Response<QuakeData>
}

object RetrofitHelper {

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USGS_REQUEST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}