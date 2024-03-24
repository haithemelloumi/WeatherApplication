package com.helloumi.todayweatherforecast.data.repository

import com.helloumi.todayweatherforecast.common.Constants.NetworkService.API_KEY_VALUE
import com.helloumi.todayweatherforecast.common.Constants.Unit.UNITS
import com.helloumi.todayweatherforecast.domain.model.response.CurrentWeatherResponse
import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.data.api.apc.ApcAPI
import com.helloumi.todayweatherforecast.domain.repository.ApcRepository
import retrofit2.Response
import java.io.IOException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApcRepositoryImpl @Inject constructor(private val apcAPI: ApcAPI) : ApcRepository {

    override fun getCurrentWeatherByCityName(cityName: String) = flow {
        emit(
            when (val response = processCallCurrentWeather(
                apcAPI.getCurrentWeatherByCityName(cityName, UNITS, API_KEY_VALUE)
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
                apcAPI.getForecastByCityName(cityName, UNITS, API_KEY_VALUE)
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
