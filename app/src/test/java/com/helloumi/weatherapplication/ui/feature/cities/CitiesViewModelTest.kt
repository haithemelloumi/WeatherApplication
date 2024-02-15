package com.helloumi.weatherapplication.ui.feature.cities

import com.helloumi.weatherapplication.domain.usecases.GetCitiesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class CitiesViewModelTest {

    @Mock
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        citiesViewModel = CitiesViewModel(getCitiesUseCase)
    }

    @Test
    fun `WHEN call addCity THEN verify useCase`() = runTest {
        // WHEN
        citiesViewModel.getCities()

        // THEN
        verify(getCitiesUseCase).execute()
    }
}
