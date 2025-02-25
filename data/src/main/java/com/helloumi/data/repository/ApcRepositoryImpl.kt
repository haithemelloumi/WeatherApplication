package com.helloumi.data.repository

import com.helloumi.data.BuildConfig.WEATHER_API_KEY
import com.helloumi.data.api.apc.ApcAPI
import com.helloumi.domain.model.response.CurrentWeatherResponse
import com.helloumi.domain.model.response.ForecastResponse
import com.helloumi.domain.model.result.CurrentWeatherResult
import com.helloumi.domain.model.result.ForecastResult
import com.helloumi.domain.repository.ApcRepository
import retrofit2.Response
import java.io.IOException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApcRepositoryImpl @Inject constructor(private val apcAPI: ApcAPI) :
    ApcRepository {

    private val units = "metric"

    override fun getCurrentWeatherByCityName(cityName: String) = flow {
        emit(
            when (val response = processCallCurrentWeather(
                apcAPI.getCurrentWeatherByCityName(cityName, units, appId = WEATHER_API_KEY)
            )) {
                is CurrentWeatherResponse -> CurrentWeatherResult.Success(response)
                is Int -> CurrentWeatherResult.ServerError
                else -> CurrentWeatherResult.ServerUnavailable
            }
        )
    }

    override fun getForecastByCityName(cityName: String) = flow {
        emit(
            when (val response = processCallForecast(
                apcAPI.getForecastByCityName(cityName, units, WEATHER_API_KEY)
            )) {
                is ForecastResponse -> ForecastResult.Success(response)
                is Int -> ForecastResult.ServerError
                else -> ForecastResult.ServerUnavailable
            }
        )
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun processCallCurrentWeather(responseCall: Response<CurrentWeatherResponse>): Any? {
        return try {
            if (responseCall.isSuccessful) {
                responseCall.body()
            } else {
                responseCall.code()
            }
        } catch (e: IOException) {
            CurrentWeatherResult.ServerUnavailable
        }
    }

    private fun processCallForecast(responseCall: Response<ForecastResponse>): Any? {
        return try {
            if (responseCall.isSuccessful) {
                responseCall.body()
            } else {
                responseCall.code()
            }
        } catch (e: IOException) {
            ForecastResult.ServerUnavailable
        }
    }
}
