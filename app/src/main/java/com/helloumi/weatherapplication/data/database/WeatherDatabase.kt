package com.helloumi.weatherapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helloumi.weatherapplication.data.datasource.CitiesForSearchDao
import com.helloumi.weatherapplication.data.datasource.CurrentWeatherDao
import com.helloumi.weatherapplication.data.datasource.ForecastDao
import com.helloumi.weatherapplication.data.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.data.entity.CurrentWeatherEntity
import com.helloumi.weatherapplication.data.entity.ForecastEntity

@Database(
    entities = [ForecastEntity::class, CurrentWeatherEntity::class, CitiesForSearchEntity::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao

    companion object {
        const val DATABASE_NAME = "weather_database.db"
    }
}
