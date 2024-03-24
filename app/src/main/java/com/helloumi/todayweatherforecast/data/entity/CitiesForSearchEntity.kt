package com.helloumi.todayweatherforecast.data.entity

import androidx.room.*
import com.helloumi.todayweatherforecast.domain.model.HitsItem

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

    constructor(hitsItem: HitsItem?) : this(
        country = hitsItem?.country,
        importance = hitsItem?.importance,
        administrative = hitsItem?.administrative?.first(),
        coord = CoordEntity(hitsItem?.geoloc),
        name = hitsItem?.localeNames?.first(),
        county = hitsItem?.county?.first(),
        id = hitsItem?.objectID.toString()
    )

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
