package com.helloumi.ui.feature.weatherforecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.domain.model.result.CurrentWeatherResult
import com.helloumi.domain.model.result.ForecastResult
import com.helloumi.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.domain.usecases.GetForecastUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAndForecastViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _currentWeatherUiState: MutableStateFlow<CurrentWeatherResult> =
        MutableStateFlow(CurrentWeatherResult.Loading)
    val currentWeatherUiState = _currentWeatherUiState.asStateFlow()

    private val _forecastResponseUiState: MutableStateFlow<ForecastResult> =
        MutableStateFlow(ForecastResult.Loading)
    val forecastResponseUiState = _forecastResponseUiState.asStateFlow()

    fun getWeather(cityName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
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

        viewModelScope.launch(dispatcherProvider.io) {
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
