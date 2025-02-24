package com.helloumi.ui.feature.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.domain.usecases.RemoveCityUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProvider
import com.helloumi.ui.utils.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val removeCityUseCase: RemoveCityUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _citiesUiState: MutableStateFlow<List<CityForSearchDomain>> =
        MutableStateFlow(listOf())
    val citiesUiState = _citiesUiState.asStateFlow()

    private val isOnline: Flow<Boolean> = networkMonitor.isOnline

    private val _isInternetAvailable: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInternetAvailable = _isInternetAvailable.asStateFlow()

    fun getCities() {
        viewModelScope.launch(dispatcherProvider.io) {
            getCitiesUseCase().collectLatest { cities ->
                _citiesUiState.value = cities
            }
        }
    }

    fun collectIsOnline() {
        viewModelScope.launch(dispatcherProvider.io) {
            isOnline.collectLatest {
                _isInternetAvailable.value = it
            }
        }
    }

    fun deleteCity(city: CityForSearchDomain) {
        viewModelScope.launch(dispatcherProvider.io) {
            removeCityUseCase(city)
        }
    }
}
