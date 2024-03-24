package com.helloumi.todayweatherforecast.data.repository

import androidx.annotation.OpenForTesting
import com.helloumi.todayweatherforecast.data.mapper.CityForSearchDomainMapper
import com.helloumi.todayweatherforecast.data.datasource.CitiesForSearchDao
import com.helloumi.todayweatherforecast.data.entity.CityForSearchEntity
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.repository.CitiesForSearchRepository
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

    override fun getCityByName(cityName: String?) = citiesForSearchDao.getCityByName(cityName)

    override suspend fun insertCity(cityDomain: CityForSearchDomain) =
        citiesForSearchDao.insertElement(cityForSearchDomainMapper.toCityEntity(cityDomain))

    override suspend fun insertCities(cities: List<CityForSearchDomain>) =
        citiesForSearchDao.insertList(cityForSearchDomainMapper.toCitiesEntities(cities))

    override fun delete(city: CityForSearchEntity) = citiesForSearchDao.delete(city)

    override suspend fun deleteCities() = citiesForSearchDao.deleteCities()

    override suspend fun getCount() = citiesForSearchDao.getCount()
}
