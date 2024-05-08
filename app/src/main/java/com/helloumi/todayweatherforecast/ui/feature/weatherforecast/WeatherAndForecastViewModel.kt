package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAndForecastViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _currentWeatherUiState: MutableState<CurrentWeatherResult> =
        mutableStateOf(CurrentWeatherResult.Loading)
    val currentWeatherUiState: MutableState<CurrentWeatherResult> get() = _currentWeatherUiState

    private val _forecastResponseUiState: MutableState<ForecastResult> =
        mutableStateOf(ForecastResult.Loading)
    val forecastResponseUiState: MutableState<ForecastResult> get() = _forecastResponseUiState

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
