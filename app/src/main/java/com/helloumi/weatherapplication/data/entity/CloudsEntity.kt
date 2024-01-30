package com.helloumi.weatherapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Clouds")
data class CloudsEntity(
    @ColumnInfo(name = "all")
    var all: Int
)
