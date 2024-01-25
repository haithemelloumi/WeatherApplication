package com.helloumi.weatherapplication.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(

    @Json(name = "deg")
    val deg: Double?,

    @Json(name = "speed")
    val speed: Double?
)
