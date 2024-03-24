package com.helloumi.todayweatherforecast.data.api.apc

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.helloumi.todayweatherforecast.domain.model.response.CurrentWeatherResponse
import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse

interface ApcAPI {

    @GET("weather?")
    suspend fun getCurrentWeatherByCityName(
        @Query("q")
        cityName: String,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getForecastByCityName(
        @Query("q")
        cityName: String,
        @Query("units")
        units: String,
        @Query("appid")
        appId: String
    ): Response<ForecastResponse>
}
