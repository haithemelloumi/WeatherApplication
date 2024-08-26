package com.helloumi.domain.repository

import com.helloumi.domain.model.result.CurrentWeatherResult
import com.helloumi.domain.model.result.ForecastResult
import kotlinx.coroutines.flow.Flow

interface ApcRepository {

    /**
     * Requests current weather to the APC services.
     *
     * @param cityName the name city.
     *
     * @return a CurrentWeatherResult Flow
     */
    fun getCurrentWeatherByCityName(cityName: String): Flow<CurrentWeatherResult>

    /**
     * Requests forecast to the APC services.
     *
     * @param cityName the name city.
     *
     * @return a ForecastResult Flow
     */
    fun getForecastByCityName(cityName: String): Flow<ForecastResult>
}
