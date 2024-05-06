package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
import com.helloumi.todayweatherforecast.ui.feature.common.CircularProgressIndicatorLoader
import com.helloumi.todayweatherforecast.ui.feature.common.WeatherToolbar
import com.helloumi.todayweatherforecast.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.todayweatherforecast.ui.theme.Dimens.INLINE_SM
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_MD
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_SM
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_XS
import com.helloumi.todayweatherforecast.ui.theme.Dimens.STACK_XXL
import com.helloumi.todayweatherforecast.ui.theme.Dimens.TEXT_SIZE_TITLE
import com.helloumi.todayweatherforecast.ui.theme.Dimens.TEXT_SIZE_VERY_BIG
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_40

/*
Added to display preview because The previews system is not capable
of constructing all of the parameters passed to a ViewModel
*/
@Composable
fun WeatherAndForecast(
    cityName: String,
    onClickBack: () -> Unit
) {

    val context: Context = LocalContext.current
    val viewModel = hiltViewModel<WeatherAndForecastViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getWeather(cityName)
        viewModel.getForecast(cityName)
    }
    val uiModel = viewModel.getUiModel(context, cityName)

    val weatherState = viewModel.currentWeatherUiState
    val forecastState = viewModel.forecastResponseUiState

    WeatherAndForecast(
        onClickBack,
        uiModel,
        weatherState,
        forecastState
    )
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun WeatherAndForecast(
    onClickBack: () -> Unit,
    uiModel: WeatherForecastUiModel,
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

        WeatherToolbar(
            toolbarText = uiModel.screenTitle,
            scrollState.firstVisibleItemScrollOffset,
            onBackClick = onClickBack,
            isArrowBackVisible = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(STACK_XS))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(
                text = uiModel.cityName,
                modifier = Modifier
                    .padding(start = INLINE_SM, top = STACK_MD),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TEXT_SIZE_VERY_BIG
                )
            )
            Text(
                text = uiModel.currentDate,
                modifier = Modifier.padding(start = INLINE_SM, top = STACK_MD),
                style = TextStyle(fontSize = TEXT_SIZE_TITLE)
            )

            /////////////////////// WEATHER ///////////////////////
            when (val currentWeatherValue = currentWeather.value) {
                is CurrentWeatherResult.Success -> {

                    Weather(
                        currentWeatherValue,
                        uiModel.noDataLabel
                    )

                    Spacer(modifier = Modifier.size(STACK_SM))

                    DailyItems(uiModel, currentWeatherValue)

                    Spacer(modifier = Modifier.size(STACK_XXL))

                    Text(
                        text = uiModel.forecastLabel,
                        modifier = Modifier.padding(start = STACK_SM),
                        style = TextStyle(fontSize = TEXT_SIZE_TITLE)
                    )
                }

                is CurrentWeatherResult.Loading -> {
                    CircularProgressIndicatorLoader()
                }

                is CurrentWeatherResult.ServerError -> {
                    ErrorMessage(uiModel.serverErrorLabel)
                }

                is CurrentWeatherResult.ServerUnavailable -> {
                    ErrorMessage(uiModel.serverUnreachableLabel)
                }
            }
            /////////////////////// END WEATHER ///////////////////////


            /////////////////////// FORECAST /////////////////////////
            Spacer(modifier = Modifier.size(STACK_SM))
            when (val forecastResultValue = forecastResult.value) {
                is ForecastResult.Success -> {
                    Forecast(forecastResultValue, uiModel.noDataLabel)
                }

                is ForecastResult.Loading -> {
                    CircularProgressIndicatorLoader()
                }

                is ForecastResult.ServerError -> {
                    ErrorMessage(uiModel.serverErrorLabel)
                }

                is ForecastResult.ServerUnavailable -> {
                    ErrorMessage(uiModel.serverUnreachableLabel)
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
    val uiModel = WeatherForecastUiModel(
        cityName = "cityName",
        currentDate = "currentDate",
        screenTitle = "screenTitle",
        forecastLabel = "forecastLabel",
        feelsLikeLabel = "feelsLikeLabel",
        visibilityLabel = "visibilityLabel",
        humidityLabel = "humidityLabel",
        windSpeed = "windSpeed",
        airPressureLabel = "airPressureLabel",
        noDataLabel = "airPressureLabel",
        serverUnreachableLabel = "serverUnreachableLabel",
        serverErrorLabel = "serverErrorLabel"
    )
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

    WeatherAndForecast(
        onClickBack = {},
        uiModel = uiModel,
        currentWeather = currentWeather,
        forecastResult = forecastResult
    )
}

@Preview
@Composable
fun ErrorMessagePreview() {
    ErrorMessage("NoData")
}
