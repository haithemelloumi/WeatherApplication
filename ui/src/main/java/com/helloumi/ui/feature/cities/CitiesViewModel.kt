package com.helloumi.ui.feature.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.domain.usecases.RemoveCityUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProvider
import com.helloumi.ui.utils.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val removeCityUseCase: RemoveCityUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CitiesScreenState())
    val uiState: StateFlow<CitiesScreenState> = _uiState.asStateFlow()

    private val _effect = Channel<CitiesEffect>()
    val effect: Flow<CitiesEffect> = _effect.receiveAsFlow()

    fun processIntent(intent: CitiesIntent) {
        viewModelScope.launch(dispatcherProvider.io) {
            when (intent) {
                is CitiesIntent.LoadInitialData -> {
                    //⚠️ Important: We run loadCities() in a separate coroutine.
                    // This function collects an infinite flow (collectLatest) and would block the execution of observeInternetStatus().
                    // Running both in parallel allows you to observe the network status immediately.
                    launch { loadCities() }
                    observeInternetStatus()
                }

                is CitiesIntent.DeleteCity -> {
                    deleteCityInternal(intent.city)
                }

                is CitiesIntent.CityTapped -> {
                    _effect.send(
                        CitiesEffect.NavigateToCityDetails(
                            intent.city,
                            _uiState.value.isInternetAvailable
                        )
                    )
                }

                CitiesIntent.AddCityButtonTapped -> {
                    _effect.send(CitiesEffect.NavigateToAddCity)
                }
            }
        }
    }

    suspend fun loadCities() {
        _uiState.update { it.copy(isLoadingCities = true) }
        getCitiesUseCase().collectLatest { citiesResult ->
            _uiState.update {
                it.copy(
                    isLoadingCities = false,
                    cities = citiesResult
                )
            }
        }
    }

    fun observeInternetStatus() {
        viewModelScope.launch(dispatcherProvider.io) {
            networkMonitor.isOnline.collectLatest { isOnline ->
                _uiState.update { it.copy(isInternetAvailable = isOnline) }
            }
        }
    }

    fun deleteCityInternal(city: CityForSearchDomain) {
        removeCityUseCase(city)
    }
}
