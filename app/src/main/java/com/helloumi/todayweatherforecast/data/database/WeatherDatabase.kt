package com.helloumi.todayweatherforecast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helloumi.todayweatherforecast.data.database.WeatherDatabase.Companion.DATABASE_VERSION
import com.helloumi.todayweatherforecast.data.datasource.CitiesForSearchDao
import com.helloumi.todayweatherforecast.data.datasource.CurrentWeatherDao
import com.helloumi.todayweatherforecast.data.datasource.ForecastDao
import com.helloumi.todayweatherforecast.data.entity.CityForSearchEntity
import com.helloumi.todayweatherforecast.data.entity.CurrentWeatherEntity
import com.helloumi.todayweatherforecast.data.entity.ForecastEntity

@Database(
    entities = [ForecastEntity::class, CurrentWeatherEntity::class, CityForSearchEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun citiesForSearchDao(): CitiesForSearchDao

    companion object {
        const val DATABASE_NAME = "weather_database.db"

        const val DATABASE_VERSION = 1

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        @JvmStatic
        fun getInstance(context: Context, factory: WeatherDatabaseFactory): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, factory).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, factory: WeatherDatabaseFactory) =
            factory.build(
                context,
                DATABASE_NAME,
            )
    }
}
