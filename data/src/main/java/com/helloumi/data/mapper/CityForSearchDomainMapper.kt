package com.helloumi.data.mapper

import androidx.annotation.OpenForTesting
import com.helloumi.data.entity.CityForSearchEntity
import com.helloumi.domain.model.CityForSearchDomain
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