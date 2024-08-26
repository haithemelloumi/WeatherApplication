package com.helloumi.ui.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.helloumi.ui.theme.TodayWeatherForecastTheme
import com.helloumi.ui.feature.navigation.SetupNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TodayWeatherForecastTheme {
                SetupNavGraph(context = this)
            }
        }
    }

    companion object {
        const val CITY_KEY = "city_key"
    }
}
