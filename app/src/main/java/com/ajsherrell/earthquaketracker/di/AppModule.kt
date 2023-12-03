package com.ajsherrell.earthquaketracker.di

import android.content.Context
import com.ajsherrell.earthquaketracker.USGS_REQUEST_URL
import com.ajsherrell.earthquaketracker.api.UsgsApi
import com.ajsherrell.earthquaketracker.domain.QuakeRepository
import com.ajsherrell.earthquaketracker.repository.QuakeRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppModule {
    val usgsApi: UsgsApi
    val quakeRepository: QuakeRepository
}
class AppModuleImpl(
    private val appContext: Context,
) : AppModule {
    override val usgsApi: UsgsApi by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl(USGS_REQUEST_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsgsApi::class.java)
    }

    override val quakeRepository: QuakeRepository by lazy {
        QuakeRepositoryImpl(usgsApi)
    }
}
