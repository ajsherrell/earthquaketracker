package com.ajsherrell.earthquaketracker

import android.app.Application
import com.ajsherrell.earthquaketracker.di.AppModule
import com.ajsherrell.earthquaketracker.di.AppModuleImpl

class MyApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}
