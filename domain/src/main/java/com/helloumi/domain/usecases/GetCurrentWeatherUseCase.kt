package com.helloumi.domain.usecases

import com.helloumi.domain.repository.ApcRepository
import javax.inject.Inject

/**
 * Gets current weather.
 */
class GetCurrentWeatherUseCase @Inject constructor(private val apcRepository: ApcRepository) {

    /**
     * Executes use case.
     *
     * @return the current weather result Flow.
     */
    fun execute(cityName: String) = apcRepository.getCurrentWeatherByCityName(cityName)
}
