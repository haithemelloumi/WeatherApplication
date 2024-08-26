package com.helloumi.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherItemList(
    val weather: List<WeatherItem?>? = null
)
