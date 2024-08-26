package com.helloumi.ui.feature.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.ui.feature.addcity.AddCityActivity
import com.helloumi.ui.feature.cities.CitiesScreen
import com.helloumi.ui.feature.weatherforecast.WeatherAndForecastScreen
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.PURPLE_40
import com.helloumi.ui.theme.WHITE
import com.helloumi.ui.utils.extensions.displayToast
import com.helloumi.ui.R
import com.helloumi.ui.feature.main.MainActivity

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(
    context: Context
) {
    val title = mutableStateOf(stringResource(R.string.cities_title))
    val navController: NavHostController = rememberNavController()

    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collect {
            title.value = it.destination.route ?: ""
        }
    }

    Scaffold(
        containerColor = WHITE,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = getTitleForRoute(title.value),
                        style = TextStyle(fontSize = Dimens.TEXT_SIZE_BIG),
                        color = WHITE
                    )
                },

                ///// ADDED TO RETURN BACK //////
                navigationIcon = {
                    if (title.value != WeatherNavigation.Cities.destination) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                                contentDescription = ""
                            )
                        }
                    }
                },
                ///// ADDED TO RETURN BACK //////

                // Customize Colors here
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PURPLE_40,
                    titleContentColor = WHITE,
                    navigationIconContentColor = WHITE,
                    actionIconContentColor = WHITE
                ),
            )
        },
        content = { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = WeatherNavigation.Cities.destination
            ) {
                composable(WeatherNavigation.Cities.destination) {
                    CitiesScreen(
                        modifier = Modifier.padding(innerPadding),
                        onClickAddCityButton = { onClickAddCityButton(context = context) }
                    ) { city, isInternetAvailable ->
                        onClickCity(city, isInternetAvailable, navController, context)
                    }
                }
                composable(WeatherNavigation.WeatherAndForecast.destination) {
                    val cityName =
                        navController.previousBackStackEntry?.savedStateHandle?.get<String>(
                            MainActivity.CITY_KEY
                        )
                    if (cityName != null) {
                        WeatherAndForecastScreen(
                            cityName = cityName
                        )
                    }
                }
            }

        },
    )
}

@Composable
private fun getTitleForRoute(route: String) =
    when (route) {
        WeatherNavigation.Cities.destination -> stringResource(R.string.cities_title)
        WeatherNavigation.WeatherAndForecast.destination -> stringResource(R.string.weather_title)
        else -> ""
    }

private fun onClickAddCityButton(context: Context) {
    val myIntent = Intent(context, AddCityActivity::class.java)
    context.startActivity(myIntent)
}

private fun onClickCity(
    city: CityForSearchDomain,
    isInternetAvailable: Boolean,
    navController: NavHostController,
    context: Context
) {
    if (isInternetAvailable) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = MainActivity.CITY_KEY,
            value = city.name
        )
        navController.navigate(WeatherNavigation.WeatherAndForecast.destination)
    } else {
        context.displayToast(R.string.weather_no_connection)
    }
}
