package com.helloumi.ui.utils.date

import android.annotation.SuppressLint
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun todayDate(): String {
    val calendar = Calendar.getInstance().time
    val df = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    return df.format(calendar)
}

@SuppressLint("SimpleDateFormat")
fun getDateTime(s: Long?): DayOfWeek? {
    if (s == null) {
        return null
    } else {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            ).dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }
}

fun getDay(dt: Long?): String? {
    return dt?.let { getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
}

fun getHourOfDay(dtTxt: String?): String {
    return dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
}
