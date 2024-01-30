package com.helloumi.weatherapplication.ui.feature.addcity

import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.usecases.AddCityUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddCityViewModelTest {

    @Mock
    private lateinit var addCityUseCase: AddCityUseCase

    private lateinit var addCityViewModel: AddCityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addCityViewModel = AddCityViewModel(addCityUseCase)
    }

    @Test
    fun `WHEN call addCity THEN ensure result`() = runTest {
        // GIVEN
        val cityId = "cityId"
        val cityName = "cityName"
        val cityDomain = CityForSearchDomain(
            id = cityId,
            name = cityName
        )

        // WHEN
        addCityViewModel.addCity(cityId, cityName)

        // THEN
        verify(addCityUseCase).execute(cityDomain)
    }
}