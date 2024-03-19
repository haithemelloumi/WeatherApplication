package com.helloumi.weatherapplication.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.helloumi.weatherapplication.ui.feature.navigation.SetupNavGraph
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    // viewModels() delegate used to get
    // by view models will automatically construct the viewModels using Hilt
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel.getCities()

        setContent {

            WeatherApplicationTheme {
                navController = rememberNavController()
                navController.addOnDestinationChangedListener { _, _, _ ->
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }

                SetupNavGraph(this, navController, weatherViewModel)
            }
        }
    }

    companion object {
        const val CITY_KEY = "city_key"
    }
}
