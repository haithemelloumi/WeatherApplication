package com.helloumi.todayweatherforecast.data.entity

import androidx.room.*
import com.helloumi.todayweatherforecast.domain.model.ListItem

@Entity(tableName = "Forecast")
data class ForecastEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @Embedded
    var city: CityEntity?,

    @ColumnInfo(name = "list")
    var list: List<ListItem>?
)
