package com.helloumi.todayweatherforecast.domain.usecases

import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.repository.CitiesForSearchRepository
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
