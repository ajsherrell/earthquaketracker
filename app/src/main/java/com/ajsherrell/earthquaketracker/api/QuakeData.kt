package com.ajsherrell.earthquaketracker.api

data class QuakeData(
    val magnitude: Int,
    val location: String,
    val timeInMilliseconds: Long
)