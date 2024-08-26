package com.helloumi.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(

    @Json(name = "temp")
    val temp: Double?,

    @Json(name = "temp_min")
    var tempMin: Double?,

    @Json(name = "grnd_level")
    val grndLevel: Double?,

    @Json(name = "temp_kf")
    val tempKf: Double?,

    @Json(name ="feels_like")
    val feelsLike: Double,

    @Json(name = "humidity")
    val humidity: Int?,

    @Json(name = "pressure")
    val pressure: Double?,

    @Json(name = "sea_level")
    val seaLevel: Double?,

    @Json(name = "temp_max")
    var tempMax: Double?
) {

    fun getTempString(): String {
        return temp.toString().substringBefore(".")
    }
    fun getTempStringWithDegree(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String = "$humidity°"

    fun getTempMinString(): String {
        return tempMin.toString().substringBefore(".") + "°"
    }

    fun getTempMaxString(): String {
        return tempMax.toString().substringBefore(".") + "°"
    }
}
