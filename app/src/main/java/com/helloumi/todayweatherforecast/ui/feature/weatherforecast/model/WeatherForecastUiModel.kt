package com.helloumi.todayweatherforecast.ui.feature.weatherforecast.model

data class WeatherForecastUiModel(
    val cityName: String,
    val currentDate: String,
    val screenTitle: String,
    val forecastLabel: String,
    val feelsLikeLabel: String,
    val visibilityLabel: String,
    val humidityLabel: String,
    val windSpeed: String,
    val airPressureLabel: String,
    val noDataLabel: String,
    val serverUnreachableLabel: String,
    val serverErrorLabel: String
)
