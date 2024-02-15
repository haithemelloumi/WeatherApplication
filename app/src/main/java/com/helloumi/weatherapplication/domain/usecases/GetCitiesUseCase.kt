package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
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
    fun execute(): Flow<List<CityForSearchDomain>> = citiesForSearchRepository.getAllCities()
}
