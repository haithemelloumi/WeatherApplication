package com.helloumi.weatherapplication.ui.feature.weatherforecast

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.domain.model.result.CurrentWeatherResult
import com.helloumi.weatherapplication.domain.model.result.ForecastResult
import com.helloumi.weatherapplication.ui.feature.common.CircularProgressIndicatorLoader
import com.helloumi.weatherapplication.ui.feature.common.WeatherToolbar
import com.helloumi.weatherapplication.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.weatherapplication.ui.theme.Dimens.INLINE_SM
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_MD
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_SM
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_XS
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_XXL
import com.helloumi.weatherapplication.ui.theme.Dimens.TEXT_SIZE_TITLE
import com.helloumi.weatherapplication.ui.theme.Dimens.TEXT_SIZE_VERY_BIG
import com.helloumi.weatherapplication.ui.theme.PURPLE_40

@Composable
fun DisplayWeatherAndForecast(
    navController: NavHostController,
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

    val context: Context = LocalContext.current

    Box {

        Column {

            WeatherToolbar(
                toolbarText = context.getString(R.string.weather_title),
                scrollState.firstVisibleItemScrollOffset,
                onBackClick = { navController.popBackStack() },
                isArrowBackVisible = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(STACK_XS))

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = uiModel.cityName,
                    modifier = Modifier
                        .padding(start = INLINE_SM, top = STACK_MD)
                        .align(Alignment.CenterHorizontally),
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

                        DisplayWeather(context, currentWeatherValue)

                        Spacer(modifier = Modifier.size(STACK_SM))

                        DisplayDailyItems(uiModel, currentWeatherValue)

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
                        DisplayServerMessage(uiModel.serverErrorLabel)
                    }
                    is CurrentWeatherResult.ServerUnavailable -> {
                        DisplayServerMessage(uiModel.serverUnreachableLabel)
                    }
                }

                /////////////////////// END WEATHER ///////////////////////


                /////////////////////// FORECAST /////////////////////////
                Spacer(modifier = Modifier.size(STACK_SM))
                when (val forecastResultValue = forecastResult.value) {
                    is ForecastResult.Success -> {
                        DisplayForecast(context, forecastResultValue)
                    }
                    is ForecastResult.Loading -> {
                        CircularProgressIndicatorLoader()
                    }
                    is ForecastResult.ServerError -> {
                        DisplayServerMessage(uiModel.serverErrorLabel)
                    }
                    is ForecastResult.ServerUnavailable -> {
                        DisplayServerMessage(uiModel.serverUnreachableLabel)
                    }
                }
                /////////////////////// END FORECAST /////////////////////
            }

        }

    }

}

@Composable
private fun DisplayServerMessage(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(start = INLINE_SM, top = STACK_MD),
        style = TextStyle(fontSize = TEXT_SIZE_TITLE)
    )
}
