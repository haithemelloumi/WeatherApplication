package com.helloumi.todayweatherforecast.data.database

import android.content.Context
import androidx.room.migration.Migration

interface WeatherDatabaseFactory {
    fun build(context: Context, bddFile: String, vararg migrations: Migration): WeatherDatabase
}