package com.ajsherrell.earthquaketracker

import androidx.lifecycle.ViewModel
import com.ajsherrell.earthquaketracker.api.RetrofitHelper
import com.ajsherrell.earthquaketracker.api.UsgsApi

class QuakeViewModel: ViewModel() {

    val quakeApi: UsgsApi = RetrofitHelper.getInstance().create(UsgsApi::class.java)

}