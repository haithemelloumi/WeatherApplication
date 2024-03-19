package com.helloumi.weatherapplication.ui.feature.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.ui.feature.cities.Cities
import com.helloumi.weatherapplication.ui.feature.main.WeatherViewModel
import com.helloumi.weatherapplication.ui.feature.main.MainActivity
import com.helloumi.weatherapplication.ui.feature.weatherforecast.DisplayWeatherAndForecast

@Composable
fun SetupNavGraph(
    context: Context,
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
) {

    NavHost(
        navController = navController,
        startDestination = WeatherNavigation.Cities.destination
    ) {
        composable(WeatherNavigation.Cities.destination) {
            Cities(navController, weatherViewModel.citiesUiState.value)
        }
        composable(WeatherNavigation.WeatherAndForecast.destination) {
            val city =
                navController.previousBackStackEntry?.savedStateHandle?.get<CityForSearchDomain>(
                    MainActivity.CITY_KEY
                )
            if (city != null) {
                weatherViewModel.getWeather(city.name)
                weatherViewModel.getForecast(city.name)

                DisplayWeatherAndForecast(
                    navController,
                    weatherViewModel.getUiModel(context, city.name),
                    weatherViewModel.currentWeatherUiState,
                    weatherViewModel.forecastResponseUiState
                )
            }
        }
    }
}
