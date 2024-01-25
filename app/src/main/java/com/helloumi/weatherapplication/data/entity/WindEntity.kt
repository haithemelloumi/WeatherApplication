package com.helloumi.weatherapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Wind")
data class WindEntity(
    @ColumnInfo(name = "deg")
    val deg: Double?,
    @ColumnInfo(name = "speed")
    val speed: Double?
)