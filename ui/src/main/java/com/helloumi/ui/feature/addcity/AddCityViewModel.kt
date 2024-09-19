package com.helloumi.ui.feature.addcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.AddCityUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Add city ViewModel.
 */
// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addCityUseCase: AddCityUseCase
) : ViewModel() {

    val placeId: MutableStateFlow<String> = MutableStateFlow("")
    val placeName: MutableStateFlow<String> = MutableStateFlow("")

    fun addCity(cityId: String, cityName: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            addCityUseCase(
                CityForSearchDomain(
                    id = cityId,
                    name = cityName
                )
            )
        }
    }
}