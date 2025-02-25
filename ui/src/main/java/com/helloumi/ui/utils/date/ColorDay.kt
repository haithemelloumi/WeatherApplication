package com.helloumi.ui.utils.date

import androidx.compose.ui.graphics.Color
import com.helloumi.ui.theme.LOCH_MARA
import com.helloumi.ui.theme.MALIBU
import com.helloumi.ui.theme.PINK_80
import com.helloumi.ui.theme.PURPLE_40
import com.helloumi.ui.theme.PURPLE_80
import com.helloumi.ui.theme.PURPLE_GREY_40
import com.helloumi.ui.theme.SUPERNOVA
import org.threeten.bp.DayOfWeek

fun getColor(dayOfWeek: DayOfWeek?): Color {
    return when (dayOfWeek) {
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
