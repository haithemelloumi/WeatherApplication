package com.helloumi.weatherapplication.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.helloumi.weatherapplication.ui.feature.cities.Cities
import com.helloumi.weatherapplication.ui.feature.navigation.WeatherNavigation
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme


class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        Cities(navController)
                    }

                    composable(WeatherNavigation.WeatherAndForecast.destination) {
                        // TODO
                    }
                }

                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }*/
            }
        }
    }
}