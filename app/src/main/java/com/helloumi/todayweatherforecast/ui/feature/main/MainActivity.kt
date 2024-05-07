package com.helloumi.todayweatherforecast.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.helloumi.todayweatherforecast.ui.feature.navigation.SetupNavGraph
import com.helloumi.todayweatherforecast.ui.theme.TodayWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodayWeatherForecastTheme {
                navController = rememberNavController()
                navController.addOnDestinationChangedListener { _, _, _ ->
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                SetupNavGraph(this, navController)
            }
        }
    }

    companion object {
        const val CITY_KEY = "city_key"
    }
}
