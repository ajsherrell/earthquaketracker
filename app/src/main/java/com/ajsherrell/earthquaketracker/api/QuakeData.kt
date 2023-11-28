package com.ajsherrell.earthquaketracker.api

data class QuakeData(
    val type: String = "FeatureCollection",
    val metadata: Metadata,
    val features: List<Feature>
)

data class Metadata(
    val generated: Long,
    val url: String,
    val title: String,
    val status: Int,
    val api: String,
    val count: Int
)

data class Feature(
    val type: String = "Feature",
    val properties: Properties,
    val geometry: Geometry,
    val id: String
)

data class Properties(
    val mag: Double,
    val place: String?,
    val time: Long,
    val updated: Long,
    val tz: String?,
    val url: String,
    val detail: String,
    val felt: Int?,
    val cdi: Double?,
    val mmi: Double?,
    val alert: String?,
    val status: String,
    val tsunami: Int,
    val sig: Int,
    val net: String,
    val code: String,
    val ids: String,
    val sources: String,
    val types: String,
    val nst: Int?,
    val dmin: Double?,
    val rms: Double,
    val gap: Int?,
    val magType: String,
    val title: String
)
data class Geometry(
    val type: String = "Point",
    val coordinates: List<Double>
)
