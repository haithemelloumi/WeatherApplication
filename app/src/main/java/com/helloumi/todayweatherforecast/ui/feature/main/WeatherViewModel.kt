package com.helloumi.todayweatherforecast.ui.feature.main

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.todayweatherforecast.R
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.domain.usecases.GetCitiesUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetForecastUseCase
import com.helloumi.todayweatherforecast.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.todayweatherforecast.utils.extensions.DateUtils
import com.helloumi.todayweatherforecast.utils.extensions.resIdByName
import com.helloumi.todayweatherforecast.utils.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val dateUtils: DateUtils,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _citiesUiState: MutableState<List<CityForSearchDomain>> = mutableStateOf(listOf())
    val citiesUiState: MutableState<List<CityForSearchDomain>> get() = _citiesUiState

    val isOnline: Flow<Boolean> = networkMonitor.isOnline

    private val _currentWeatherUiState: MutableState<CurrentWeatherResult> =
        mutableStateOf(CurrentWeatherResult.Loading)
    val currentWeatherUiState: MutableState<CurrentWeatherResult> get() = _currentWeatherUiState

    private val _forecastResponseUiState: MutableState<ForecastResult> =
        mutableStateOf(ForecastResult.Loading)
    val forecastResponseUiState: MutableState<ForecastResult> get() = _forecastResponseUiState

    @DrawableRes
    var weatherIcon: Int? = null

    fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.execute().collectLatest { cities ->
                _citiesUiState.value = cities
            }
        }
    }

    fun getUiModel(context: Context, cityName: String) = WeatherForecastUiModel(
        cityName = cityName,
        currentDate = dateUtils.todayDate(),
        screenTitle = context.getString(R.string.weather_title),
        forecastLabel = context.getString(R.string.next_5_days),
        feelsLikeLabel = context.getString(R.string.feels_like),
        visibilityLabel = context.getString(R.string.visibility),
        humidityLabel = context.getString(R.string.humidity),
        windSpeed = context.getString(R.string.wind_speed),
        airPressureLabel = context.getString(R.string.air_pressure),
        noDataLabel = context.getString(R.string.no_data),
        serverUnreachableLabel = context.getString(R.string.weather_server_unreachable),
        serverErrorLabel = context.getString(R.string.weather_server_error)
    )

    fun getWeather(context: Context, cityName: String) {

        viewModelScope.launch {
            getCurrentWeatherUseCase.execute(cityName).collect { result ->
                when (result) {
                    is CurrentWeatherResult.Loading -> {
                        _currentWeatherUiState.value = CurrentWeatherResult.Loading
                    }

                    is CurrentWeatherResult.ServerError -> {
                        _currentWeatherUiState.value = CurrentWeatherResult.ServerError
                    }

                    is CurrentWeatherResult.ServerUnavailable -> {
                        _currentWeatherUiState.value = CurrentWeatherResult.ServerUnavailable
                    }

                    is CurrentWeatherResult.Success -> {
                        _currentWeatherUiState.value =
                            CurrentWeatherResult.Success(result.currentWeatherResponse)
                        weatherIcon = getWeatherIconResId(context, result)
                    }
                }

            }
        }
    }

    fun getForecast(cityName: String) {

        viewModelScope.launch {
            getForecastUseCase.execute(cityName).collect { result ->
                when (result) {
                    is ForecastResult.Loading -> {
                        _forecastResponseUiState.value = ForecastResult.Loading
                    }

                    is ForecastResult.ServerError -> {
                        _forecastResponseUiState.value = ForecastResult.ServerError
                    }

                    is ForecastResult.ServerUnavailable -> {
                        _forecastResponseUiState.value = ForecastResult.ServerUnavailable
                    }

                    is ForecastResult.Success -> {
                        _forecastResponseUiState.value =
                            ForecastResult.Success(result.forecastResponse)
                    }
                }

            }
        }
    }

    private fun getWeatherIconResId(
        context: Context,
        currentWeatherResult: CurrentWeatherResult.Success
    ) = context.resIdByName(
        "icon_${currentWeatherResult.currentWeatherResponse.weather?.get(0)?.icon}",
        "drawable"
    )
}
