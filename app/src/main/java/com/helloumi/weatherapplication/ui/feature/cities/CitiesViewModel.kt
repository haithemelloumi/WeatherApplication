package com.helloumi.weatherapplication.ui.feature.cities

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.usecases.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private val _citiesUiState: MutableState<List<CityForSearchDomain>> = mutableStateOf(listOf())
    val citiesUiState: MutableState<List<CityForSearchDomain>> get() = _citiesUiState

    fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            getCitiesUseCase.execute().collectLatest { cities ->
                _citiesUiState.value = cities
            }
        }
    }
}