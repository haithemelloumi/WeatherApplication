package com.helloumi.domain.usecases

import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.repository.CitiesForSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Gets list of cities.
 */
class GetCitiesUseCase @Inject constructor(private val citiesForSearchRepository: CitiesForSearchRepository) {

    /**
     * Executes use case.
     *
     * @return list of CityForSearchDomain Flow.
     */
    operator fun invoke(): Flow<List<CityForSearchDomain>> = citiesForSearchRepository.getAllCities()
}
