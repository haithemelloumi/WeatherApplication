package com.helloumi.todayweatherforecast.data.mapper

import androidx.annotation.OpenForTesting
import com.helloumi.todayweatherforecast.data.entity.CityForSearchEntity
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
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

    fun toCitiesDomain(cities: List<CityForSearchEntity>): List<CityForSearchDomain> {
        return cities.map { cityEntity ->
            CityForSearchDomain(
                id = cityEntity.id,
                name = cityEntity.name ?: "",
            )
        }
    }
}
