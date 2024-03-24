package com.helloumi.todayweatherforecast.domain.usecases

import com.helloumi.todayweatherforecast.data.repository.ApcRepositoryImpl
import javax.inject.Inject

/**
 * Gets current weather.
 */
class GetCurrentWeatherUseCase @Inject constructor(private val apcRepositoryImpl: ApcRepositoryImpl) {

    /**
     * Executes use case.
     *
     * @return the current weather result Flow.
     */
    fun execute(cityName: String) = apcRepositoryImpl.getCurrentWeatherByCityName(cityName)
}
