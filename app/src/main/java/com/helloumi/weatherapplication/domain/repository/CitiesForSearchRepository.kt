package com.helloumi.weatherapplication.domain.repository

import com.helloumi.weatherapplication.data.entity.CityForSearchEntity
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import kotlinx.coroutines.flow.Flow

interface CitiesForSearchRepository {

    /**
     * Gets all cities.
     *
     * @return the [CityForSearchDomain] [Flow] [List].
     */
    fun getAllCities(): Flow<List<CityForSearchDomain>>

    /**
     * Gets city by name.
     *
     * @param cityName the name of city.
     *
     * @return the [CityForSearchEntity] [Flow] [List].
     */
    fun getCityByName(cityName: String? = ""): Flow<List<CityForSearchEntity>>

    /**
     * Inserts city.
     *
     * @param cityDomain the city to insert.
     *
     * @return the resulting Flow with the id of the saved city.
     */
    suspend fun insertCity(cityDomain: CityForSearchDomain): Long

    /**
     * Inserts cities.
     *
     * @param cities the cities to insert.
     */
    suspend fun insertCities(cities: List<CityForSearchDomain>): List<Long>

    /**
     * Deletes a city.
     *
     * @param city the city to delete.
     *
     * @return the number of deleted flights.
     */
    fun delete(city: CityForSearchEntity): Int

    /**
     * Deletes all cities.
     */
    suspend fun deleteCities()

    /**
     * Gets cities count.
     *
     * @return the count of cities.
     */
    suspend fun getCount(): Int
}
