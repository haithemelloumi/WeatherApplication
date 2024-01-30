package com.helloumi.weatherapplication.data.mapper

import androidx.annotation.OpenForTesting
import com.helloumi.weatherapplication.data.entity.CityForSearchEntity
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import javax.inject.Inject

@OpenForTesting
class CityForSearchDomainMapper @Inject constructor() {

    fun toCityEntity(cityForSearchDomain: CityForSearchDomain) = CityForSearchEntity(
        cityId = cityForSearchDomain.id, cityName = cityForSearchDomain.name
    )

    fun toCitiesEntities(cities: List<CityForSearchDomain>): List<CityForSearchEntity> {
        return cities.map { cityDomain ->
            CityForSearchEntity(
                cityId = cityDomain.id,
                cityName = cityDomain.name,
            )
        }
    }
}
