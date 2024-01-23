package com.helloumi.weatherapplication.ui.feature.navigation

sealed class WeatherNavigation(val destination: String) {
    object Cities: WeatherNavigation("cities")
    object WeatherAndForecast: WeatherNavigation("weather_and_forecast")
}