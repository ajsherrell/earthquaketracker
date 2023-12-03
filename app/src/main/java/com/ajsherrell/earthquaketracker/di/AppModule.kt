package com.ajsherrell.earthquaketracker.di

import com.ajsherrell.earthquaketracker.MyApp
import com.ajsherrell.earthquaketracker.USGS_REQUEST_URL
import com.ajsherrell.earthquaketracker.api.UsgsApi
import com.ajsherrell.earthquaketracker.domain.QuakeRepository
import com.ajsherrell.earthquaketracker.repository.QuakeRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsgsApi(): UsgsApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(USGS_REQUEST_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UsgsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuakeRepository(usgsApi: UsgsApi): QuakeRepository {
        return QuakeRepositoryImpl(usgsApi)
    }
}
