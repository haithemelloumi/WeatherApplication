package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.repository.ApcRepository
import javax.inject.Inject

/**
 * Gets forecast.
 */
class GetForecastUseCase @Inject constructor(private val apcRepository: ApcRepository) {

    /**
     * Executes use case.
     *
     * @return the current forecast result Flow.
     */
    fun execute(cityName: String) = apcRepository.getForecastByCityName(cityName)
}
