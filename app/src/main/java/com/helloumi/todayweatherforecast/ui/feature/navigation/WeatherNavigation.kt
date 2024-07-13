package com.helloumi.todayweatherforecast.ui.feature.navigation

sealed class WeatherNavigation(val destination: String) {
    data object Cities: WeatherNavigation("cities")
    data object WeatherAndForecast: WeatherNavigation("weather_and_forecast")
}
