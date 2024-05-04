package com.helloumi.todayweatherforecast.ui.feature.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.ui.feature.addcity.AddCityActivity
import com.helloumi.todayweatherforecast.ui.feature.cities.Cities
import com.helloumi.todayweatherforecast.ui.feature.main.WeatherViewModel
import com.helloumi.todayweatherforecast.ui.feature.main.MainActivity
import com.helloumi.todayweatherforecast.ui.feature.weatherforecast.WeatherAndForecast

@Composable
fun SetupNavGraph(
    context: Context,
    isInternetAvailable: MutableState<Boolean>,
    navController: NavHostController,
    weatherViewModel: WeatherViewModel
) {

    NavHost(
        navController = navController,
        startDestination = WeatherNavigation.Cities.destination
    ) {
        composable(WeatherNavigation.Cities.destination) {
            Cities(
                isInternetAvailable,
                navController,
                weatherViewModel.citiesUiState.value
            ) { onClickAddCityButton(context = context) }
        }
        composable(WeatherNavigation.WeatherAndForecast.destination) {
            val city =
                navController.previousBackStackEntry?.savedStateHandle?.get<CityForSearchDomain>(
                    MainActivity.CITY_KEY
                )
            if (city != null) {
                weatherViewModel.getWeather(context, city.name)
                weatherViewModel.getForecast(city.name)
                WeatherAndForecast(
                    navController,
                    weatherViewModel.getUiModel(context, city.name),
                    weatherViewModel.weatherIcon,
                    weatherViewModel.currentWeatherUiState,
                    weatherViewModel.forecastResponseUiState
                )
            }
        }
    }
}

private fun onClickAddCityButton(context: Context) {
    val myIntent = Intent(context, AddCityActivity::class.java)
    context.startActivity(myIntent)
}
