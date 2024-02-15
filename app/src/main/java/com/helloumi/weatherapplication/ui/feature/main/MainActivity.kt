package com.helloumi.weatherapplication.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.helloumi.weatherapplication.ui.feature.cities.Cities
import com.helloumi.weatherapplication.ui.feature.cities.CitiesViewModel
import com.helloumi.weatherapplication.ui.feature.navigation.WeatherNavigation
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    // viewModels() delegate used to get
    // by view models will automatically construct the viewModels using Hilt
    private val citiesViewModel: CitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        citiesViewModel.getCities()

        setContent {

            WeatherApplicationTheme {
                navController = rememberNavController()
                navController.addOnDestinationChangedListener { _, _, _ ->
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }

                NavHost(
                    navController,
                    startDestination = WeatherNavigation.Cities.destination
                ) {

                    composable(WeatherNavigation.Cities.destination) {
                        Cities(navController, citiesViewModel.citiesUiState.value)
                    }
                    composable(WeatherNavigation.WeatherAndForecast.destination) {
                        // TODO
                    }
                }
            }
        }
    }
}