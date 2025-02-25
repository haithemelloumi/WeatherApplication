package com.helloumi.domain.usecases

import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.repository.CitiesForSearchRepository
import javax.inject.Inject

/**
 * Adds city in database.
 */
class AddCityUseCase @Inject constructor(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    suspend operator fun invoke(cityForSearchDomain: CityForSearchDomain) =
        citiesForSearchRepository.insertCity(cityForSearchDomain)
}
