package com.helloumi.todayweatherforecast.domain.usecases

import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.repository.CitiesForSearchRepository
import javax.inject.Inject


/**
 * Adds city in database.
 */
class AddCityUseCase @Inject constructor(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    suspend fun execute(cityForSearchDomain: CityForSearchDomain) =
        citiesForSearchRepository.insertCity(cityForSearchDomain)
}
