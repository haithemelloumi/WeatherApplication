package com.helloumi.weatherapplication.data.repository

import androidx.annotation.OpenForTesting
import com.helloumi.weatherapplication.data.mapper.CityForSearchDomainMapper
import com.helloumi.weatherapplication.data.datasource.CitiesForSearchDao
import com.helloumi.weatherapplication.data.entity.CityForSearchEntity
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
import javax.inject.Inject

@OpenForTesting
class CitiesForSearchRepositoryImpl @Inject constructor(
    private val citiesForSearchDao: CitiesForSearchDao,
    private val cityForSearchDomainMapper: CityForSearchDomainMapper
) : CitiesForSearchRepository {

    override fun getAllCities() = citiesForSearchDao.getCities()

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName)

    override suspend fun insertCity(cityDomain: CityForSearchDomain) =
        citiesForSearchDao.insertElement(cityForSearchDomainMapper.toCityEntity(cityDomain))

    override suspend fun insertCities(cities: List<CityForSearchDomain>) =
        citiesForSearchDao.insertList(cityForSearchDomainMapper.toCitiesEntities(cities))

    override fun delete(city: CityForSearchEntity) = citiesForSearchDao.delete(city)

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
