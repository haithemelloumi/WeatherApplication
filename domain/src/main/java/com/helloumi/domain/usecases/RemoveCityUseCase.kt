package com.helloumi.domain.usecases

import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.repository.CitiesForSearchRepository
import javax.inject.Inject

/**
 * Removes city in database.
 */
class RemoveCityUseCase @Inject constructor(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     */
    operator fun invoke(cityForSearchDomain: CityForSearchDomain) =
        citiesForSearchRepository.delete(cityForSearchDomain)
}
