package com.helloumi.todayweatherforecast.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherItemList(
    val weather: List<WeatherItem?>? = null
) : Parcelable
