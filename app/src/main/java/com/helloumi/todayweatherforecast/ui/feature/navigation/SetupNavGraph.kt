package com.helloumi.todayweatherforecast.ui.feature.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.ui.feature.addcity.AddCityActivity
import com.helloumi.todayweatherforecast.ui.feature.cities.Cities
import com.helloumi.todayweatherforecast.ui.feature.main.MainActivity
import com.helloumi.todayweatherforecast.ui.feature.weatherforecast.WeatherAndForecast

@Composable
fun SetupNavGraph(
    context: Context,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = WeatherNavigation.Cities.destination
    ) {
        composable(WeatherNavigation.Cities.destination) {
            Cities(
                navController = navController,
            ) { onClickAddCityButton(context = context) }
        }
        composable(WeatherNavigation.WeatherAndForecast.destination) {
            val city =
                navController.previousBackStackEntry?.savedStateHandle?.get<CityForSearchDomain>(
                    MainActivity.CITY_KEY
                )
            if (city != null) {
                WeatherAndForecast(
                    cityName = city.name,
                    onClickBack = { onClickBack(navController) }
                )
            }
        }
    }
}

private fun onClickAddCityButton(context: Context) {
    val myIntent = Intent(context, AddCityActivity::class.java)
    context.startActivity(myIntent)
}

private fun onClickBack(navController: NavHostController) {
    navController.popBackStack()
}
