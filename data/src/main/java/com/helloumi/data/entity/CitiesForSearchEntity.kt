package com.helloumi.data.entity

import androidx.room.*

@Entity(tableName = "CityForSearch")
data class CityForSearchEntity(
    @ColumnInfo(name = "administrative")
    val administrative: String?,
    @ColumnInfo(name = "Country")
    val country: String?,
    @Embedded
    val coord: CoordEntity?,
    @ColumnInfo(name = "fullName")
    val name: String?,
    @ColumnInfo(name = "county")
    val county: String?,
    @ColumnInfo(name = "importance")
    val importance: Int?,
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: String
) {

    constructor(cityId: String, cityName: String) : this(
        id = cityId,
        name = cityName,
        country = null,
        importance = null,
        administrative = null,
        coord = null,
        county = null,
    )
}
