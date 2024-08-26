package com.helloumi.domain.model.result

import com.helloumi.domain.model.response.CurrentWeatherResponse

/**
 * Current Weather result, from web service requests.
 */
sealed class CurrentWeatherResult {

    /**
     * Loading result.
     */
    object Loading : CurrentWeatherResult()

    /**
     * Success result.
     *
     * @property currentWeatherResponse downloaded currentWeatherResponse.
     */
    class Success(val currentWeatherResponse: CurrentWeatherResponse) : CurrentWeatherResult()

    /**
     * Unavailable server result.
     */
    object ServerUnavailable : CurrentWeatherResult()

    /**
     * Error on server result.
     */
    object ServerError : CurrentWeatherResult()
}
