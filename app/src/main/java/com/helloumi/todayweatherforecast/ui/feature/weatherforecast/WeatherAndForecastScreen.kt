package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.todayweatherforecast.R
import com.helloumi.todayweatherforecast.domain.model.City
import com.helloumi.todayweatherforecast.domain.model.Clouds
import com.helloumi.todayweatherforecast.domain.model.Coord
import com.helloumi.todayweatherforecast.domain.model.Main
import com.helloumi.todayweatherforecast.domain.model.Sys
import com.helloumi.todayweatherforecast.domain.model.Wind
import com.helloumi.todayweatherforecast.domain.model.response.CurrentWeatherResponse
import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.ui.feature.circularprogress.CircularProgressIndicatorLoader
import com.helloumi.todayweatherforecast.ui.theme.Dimens.INLINE_SM
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_MD
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_SM
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_XS
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_XXL
import com.helloumi.todayweatherforecast.ui.theme.Dimens.TEXT_SIZE_TITLE
import com.helloumi.todayweatherforecast.ui.theme.Dimens.TEXT_SIZE_VERY_BIG
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_40
import com.helloumi.todayweatherforecast.ui.utils.extensions.todayDate

/*
Added to display preview because The previews system is not capable
of constructing all of the parameters passed to a ViewModel
*/
@Composable
fun WeatherAndForecastScreen(
    cityName: String,
    viewModel: WeatherAndForecastViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getWeather(cityName)
        viewModel.getForecast(cityName)
    }

    val weatherState = viewModel.currentWeatherUiState
    val forecastState = viewModel.forecastResponseUiState

    WeatherAndForecastContent(
        cityName,
        weatherState,
        forecastState
    )
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun WeatherAndForecastContent(
    cityName: String,
    currentWeather: MutableState<CurrentWeatherResult>,
    forecastResult: MutableState<ForecastResult>
) {

    //LazyList scroll position
    val scrollState = rememberLazyListState()

    //System UI state
    val systemUiController = rememberSystemUiController()

    val isDarkTheme = isSystemInDarkTheme()
    systemUiController.setStatusBarColor(
        color = PURPLE_40,
        darkIcons =
        if (isDarkTheme) false
        else scrollState.firstVisibleItemScrollOffset != 0
    )

    Column {

        Spacer(modifier = Modifier.size(STACK_XS))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = cityName,
                modifier = Modifier
                    .padding(start = INLINE_SM, top = STACK_MD),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE_VERY_BIG
                )
            )
            Text(
                text = todayDate(),
                modifier = Modifier.padding(start = INLINE_SM, top = STACK_MD),
                style = TextStyle(fontSize = TEXT_SIZE_TITLE)
            )

            /////////////////////// WEATHER ///////////////////////
            when (val currentWeatherValue = currentWeather.value) {
                is CurrentWeatherResult.Success -> {

                    Weather(
                        currentWeatherValue,
                        stringResource(id = R.string.no_data)
                    )

                    Spacer(modifier = Modifier.size(STACK_SM))

                    DailyItems(currentWeatherValue)

                    Spacer(modifier = Modifier.size(STACK_XXL))

                    Text(
                        text = stringResource(id = R.string.next_5_days),
                        modifier = Modifier.padding(start = STACK_SM),
                        style = TextStyle(fontSize = TEXT_SIZE_TITLE)
                    )
                }

                is CurrentWeatherResult.Loading -> {
                    CircularProgressIndicatorLoader()
                }

                is CurrentWeatherResult.ServerError -> {
                    ErrorMessage(stringResource(id = R.string.weather_server_error))
                }

                is CurrentWeatherResult.ServerUnavailable -> {
                    ErrorMessage(stringResource(id = R.string.weather_server_unreachable))
                }
            }
            /////////////////////// END WEATHER ///////////////////////


            /////////////////////// FORECAST /////////////////////////
            Spacer(modifier = Modifier.size(STACK_SM))
            when (val forecastResultValue = forecastResult.value) {
                is ForecastResult.Success -> {
                    Forecast(forecastResultValue)
                }

                is ForecastResult.Loading -> {
                    CircularProgressIndicatorLoader()
                }

                is ForecastResult.ServerError -> {
                    ErrorMessage(stringResource(id = R.string.weather_server_error))
                }

                is ForecastResult.ServerUnavailable -> {
                    ErrorMessage(stringResource(id = R.string.weather_server_unreachable))
                }
            }
            Spacer(modifier = Modifier.size(STACK_SM))
            /////////////////////// END FORECAST /////////////////////
        }

    }

}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(start = INLINE_SM, top = STACK_MD),
        style = TextStyle(fontSize = TEXT_SIZE_TITLE)
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun WeatherAndForecastPreview() {
    val currentWeather: MutableState<CurrentWeatherResult> = mutableStateOf(
        CurrentWeatherResult.Success(
            CurrentWeatherResponse(
                visibility = 100,
                timezone = 99,
                main = Main(
                    temp = 1000.0,
                    tempMin = 1000.0,
                    grndLevel = 1000.0,
                    tempKf = 1000.0,
                    feelsLike = 1000.0,
                    humidity = 100,
                    pressure = 100.0,
                    seaLevel = 100.0,
                    tempMax = 100.0
                ),
                clouds = Clouds(all = 100),
                sys = Sys("ff"),
                dt = 1000,
                coord = Coord(100.0, 33.0),
                weather = listOf(),
                name = "name",
                cod = 23,
                id = 11,
                base = "gg",
                wind = Wind(2.0, 4.0)
            )
        )
    )

    val forecastResult: MutableState<ForecastResult> = mutableStateOf(
        ForecastResult.Success(
            ForecastResponse(
                city = City(
                    country = null,
                    coord = null,
                    name = "name",
                    id = 2
                ),
                cnt = 3,
                cod = "df",
                message = 23.9,
                list = listOf()
            )
        )
    )

    WeatherAndForecastContent(
        cityName = "cityName",
        currentWeather = currentWeather,
        forecastResult = forecastResult
    )
}

@Preview
@Composable
fun ErrorMessagePreview() {
    ErrorMessage("NoData")
}
