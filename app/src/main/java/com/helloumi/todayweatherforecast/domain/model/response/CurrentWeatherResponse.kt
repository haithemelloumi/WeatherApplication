package com.helloumi.todayweatherforecast.domain.model.response

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.helloumi.todayweatherforecast.domain.model.Clouds
import com.helloumi.todayweatherforecast.domain.model.Coord
import com.helloumi.todayweatherforecast.domain.model.Main
import com.helloumi.todayweatherforecast.domain.model.Sys
import com.helloumi.todayweatherforecast.domain.model.WeatherItem
import com.helloumi.todayweatherforecast.domain.model.Wind
import com.helloumi.todayweatherforecast.ui.theme.LOCH_MARA
import com.helloumi.todayweatherforecast.ui.theme.MALIBU
import com.helloumi.todayweatherforecast.ui.theme.PINK_80
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_40
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_80
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_GREY_40
import com.helloumi.todayweatherforecast.ui.theme.SUPERNOVA
import kotlinx.parcelize.Parcelize
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Parcelize
@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(

    @Json(name = "visibility")
    val visibility: Int? = null,

    @Json(name = "timezone")
    val timezone: Int? = null,

    @Json(name = "main")
    val main: Main? = null,

    @Json(name = "clouds")
    val clouds: Clouds? = null,

    @Json(name = "sys")
    val sys: Sys? = null,

    @Json(name = "dt")
    val dt: Int? = null,

    @Json(name = "coord")
    val coord: Coord? = null,

    @Json(name = "weather")
    val weather: List<WeatherItem?>? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "cod")
    val cod: Int? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "base")
    val base: String? = null,

    @Json(name = "wind")
    val wind: Wind? = null

) : Parcelable {
    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }

    fun getColor(): Color {
        return when (dt?.let { getDateTime(it.toLong()) }) {
            DayOfWeek.MONDAY -> PINK_80
            DayOfWeek.TUESDAY -> PURPLE_GREY_40
            DayOfWeek.WEDNESDAY -> PURPLE_80
            DayOfWeek.THURSDAY -> PURPLE_40
            DayOfWeek.FRIDAY -> LOCH_MARA
            DayOfWeek.SATURDAY -> MALIBU
            DayOfWeek.SUNDAY -> SUPERNOVA
            else -> SUPERNOVA
        }
    }
}
