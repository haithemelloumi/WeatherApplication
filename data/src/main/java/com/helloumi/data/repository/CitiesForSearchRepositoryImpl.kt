package com.helloumi.data.repository

import androidx.annotation.OpenForTesting
import com.helloumi.data.datasource.CitiesForSearchDao
import com.helloumi.data.mapper.CityForSearchDomainMapper
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.repository.CitiesForSearchRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OpenForTesting
class CitiesForSearchRepositoryImpl @Inject constructor(
    private val citiesForSearchDao: CitiesForSearchDao,
    private val cityForSearchDomainMapper: CityForSearchDomainMapper
) : CitiesForSearchRepository {

    override fun getAllCities() = citiesForSearchDao.getCities().map {
        cityForSearchDomainMapper.toCitiesDomain(it)
    }

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName).map {
        cityForSearchDomainMapper.toCitiesDomain(it)
    }

    override suspend fun insertCity(cityDomain: CityForSearchDomain) =
        citiesForSearchDao.insertElement(
            cityForSearchDomainMapper
                .toCityEntity(cityDomain)
        )

    override suspend fun insertCities(cities: List<CityForSearchDomain>) =
        citiesForSearchDao.insertList(
            cityForSearchDomainMapper
                .toCitiesEntities(cities)
        )

    override fun delete(cityDomain: CityForSearchDomain) =
        citiesForSearchDao.delete(
            cityForSearchDomainMapper
                .toCityEntity(cityDomain)
        )

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
