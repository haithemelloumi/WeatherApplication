package com.helloumi.todayweatherforecast.domain.model.response

import android.os.Parcelable
import com.helloumi.todayweatherforecast.domain.model.City
import com.helloumi.todayweatherforecast.domain.model.ListItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ForecastResponse(

    @Json(name = "city")
    val city: City?,

    @Json(name = "cnt")
    val cnt: Int?,

    @Json(name = "cod")
    val cod: String?,

    @Json(name = "message")
    val message: Double?,

    @Json(name = "list")
    val list: List<ListItem>?
) : Parcelable