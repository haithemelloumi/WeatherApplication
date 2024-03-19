package com.helloumi.weatherapplication.ui.feature.main

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.model.result.CurrentWeatherResult
import com.helloumi.weatherapplication.domain.model.result.ForecastResult
import com.helloumi.weatherapplication.domain.usecases.GetCitiesUseCase
import com.helloumi.weatherapplication.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.weatherapplication.domain.usecases.GetForecastUseCase
import com.helloumi.weatherapplication.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.weatherapplication.utils.extensions.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    private val dateUtils: DateUtils
) : ViewModel() {

    private val _citiesUiState: MutableState<List<CityForSearchDomain>> = mutableStateOf(listOf())
    val citiesUiState: MutableState<List<CityForSearchDomain>> get() = _citiesUiState

    private val _currentWeatherUiState: MutableState<CurrentWeatherResult> =
        mutableStateOf(CurrentWeatherResult.Loading)
    val currentWeatherUiState: MutableState<CurrentWeatherResult> get() = _currentWeatherUiState

    private val _forecastResponseUiState: MutableState<ForecastResult> =
        mutableStateOf(ForecastResult.Loading)
    val forecastResponseUiState: MutableState<ForecastResult> get() = _forecastResponseUiState
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

    fun getWeather(cityName: String) {

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
}
