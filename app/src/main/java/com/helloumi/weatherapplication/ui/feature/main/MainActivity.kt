package com.helloumi.weatherapplication.ui.feature.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.helloumi.weatherapplication.ui.feature.navigation.SetupNavGraph
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private val isInternetAvailable: MutableState<Boolean> = mutableStateOf(false)

    // viewModels() delegate used to get
    // by view models will automatically construct the viewModels using Hilt
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel.getCities()
        collectInternet()

        setContent {
            WeatherApplicationTheme {
                navController = rememberNavController()
                navController.addOnDestinationChangedListener { _, _, _ ->
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }
                SetupNavGraph(this, isInternetAvailable, navController, weatherViewModel)
            }
        }
    }

    private fun collectInternet() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                weatherViewModel.isOnline.collectLatest {
                    isInternetAvailable.value = it
                }
            }
        }
    }

    companion object {
        const val CITY_KEY = "city_key"
    }
}
