package com.helloumi.ui.feature.cities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProvider
import com.helloumi.ui.utils.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getCitiesUseCase: GetCitiesUseCase,
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _citiesUiState: MutableState<List<CityForSearchDomain>> = mutableStateOf(listOf())
    val citiesUiState: MutableState<List<CityForSearchDomain>> get() = _citiesUiState

    private val isOnline: Flow<Boolean> = networkMonitor.isOnline

    private val _isInternetAvailable: MutableState<Boolean> = mutableStateOf(false)
    val isInternetAvailable: MutableState<Boolean> get() = _isInternetAvailable

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
}
