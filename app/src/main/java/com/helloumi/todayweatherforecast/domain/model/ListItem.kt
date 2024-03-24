package com.helloumi.todayweatherforecast.domain.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.helloumi.todayweatherforecast.ui.theme.LOCH_MARA
import com.helloumi.todayweatherforecast.ui.theme.MALIBU
import com.helloumi.todayweatherforecast.ui.theme.PINK_80
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_40
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_80
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_GREY_40
import com.helloumi.todayweatherforecast.ui.theme.SUPERNOVA
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
@JsonClass(generateAdapter = true)
data class ListItem(

    @Json(name = "dt")
    val dt: Long?,

    @Json(name = "rain")
    val rain: Rain?,

    @Json(name = "dt_txt")
    val dtTxt: String?,

    @Json(name = "snow")
    val snow: Snow?,

    @Json(name = "weather")
    val weather: List<WeatherItem?>?,

    @Json(name = "main")
    val main: Main?,

    @Json(name = "clouds")
    val clouds: Clouds?,

    @Json(name = "sys")
    val sys: Sys?,

    @Json(name = "wind")
    val wind: Wind?
) : Parcelable {
    fun getWeatherItem(): WeatherItem? {
        return weather?.first()
    }

    fun getDay(): String? {
        return dt?.let { getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
    }

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
        return when (dt?.let { getDateTime(it) }) {
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

    fun getHourOfDay(): String {
        return dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
    }
}

