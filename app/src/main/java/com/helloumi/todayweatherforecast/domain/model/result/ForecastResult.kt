package com.helloumi.todayweatherforecast.domain.model.result

import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse

/**
 * Current Weather result, from web service requests.
 */
sealed class ForecastResult {

    /**
     * Loading result.
     */
    object Loading : ForecastResult()

    /**
     * Success result.
     *
     * @property forecastResponse downloaded forecastResponse.
     */
    class Success(val forecastResponse: ForecastResponse) : ForecastResult()

    /**
     * Unavailable server result.
     */
    object ServerUnavailable : ForecastResult()

    /**
     * Error on server result.
     */
    object ServerError : ForecastResult()
}
