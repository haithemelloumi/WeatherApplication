package com.helloumi.weatherapplication.data.entity

import androidx.room.*
import com.helloumi.weatherapplication.domain.model.WeatherItem

@Entity(tableName = "CurrentWeather")
data class CurrentWeatherEntity(
    @ColumnInfo(name = "visibility")
    var visibility: Int?,
    @ColumnInfo(name = "timezone")
    var timezone: Int?,
    @Embedded
    var main: MainEntity?,
    @Embedded
    var clouds: CloudsEntity?,
    @ColumnInfo(name = "dt")
    var dt: Long?,
    @ColumnInfo(name = "weather")
    val weather: List<WeatherItem?>? = null,
    @ColumnInfo(name = "name")
    val name: String?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "base")
    val base: String?,
    @Embedded
    val wind: WindEntity?
)