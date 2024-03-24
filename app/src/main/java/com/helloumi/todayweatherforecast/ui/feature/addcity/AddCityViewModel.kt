package com.helloumi.todayweatherforecast.ui.feature.addcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import kotlinx.coroutines.Dispatchers
import com.helloumi.todayweatherforecast.domain.usecases.AddCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val addCityUseCase: AddCityUseCase
) : ViewModel() {

    fun addCity(cityId: String, cityName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addCityUseCase.execute(
                CityForSearchDomain(
                    id = cityId,
                    name = cityName
                )
            )
        }
    }
}
