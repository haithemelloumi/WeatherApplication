package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
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
