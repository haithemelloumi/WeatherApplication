package com.helloumi.weatherapplication.ui.feature.navigation

sealed class WeatherNavigation(val destination: String) {
    object Cities: WeatherNavigation("cities")
    object AddCity: WeatherNavigation("add_city")
    object WeatherAndForecast: WeatherNavigation("weather_and_forecast")
}