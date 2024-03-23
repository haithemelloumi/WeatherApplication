package com.helloumi.weatherapplication.utils.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class DateUtils @Inject constructor() {
    fun todayDate(): String {
        val calendar = Calendar.getInstance().time
        val df = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
        return df.format(calendar)
    }
}
