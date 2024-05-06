package com.helloumi.todayweatherforecast.ui.feature.cities

import com.helloumi.todayweatherforecast.domain.usecases.GetCitiesUseCase
import com.helloumi.todayweatherforecast.common.TestViewModelScopeRule
import com.helloumi.todayweatherforecast.utils.network.NetworkMonitor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class CitiesViewModelTest {

    @get:Rule
    val dispatcherRule = TestViewModelScopeRule()

    @Mock
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Mock
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        citiesViewModel = CitiesViewModel(
            getCitiesUseCase,
            networkMonitor
        )
    }

    @Test
    fun `WHEN call getCities THEN verify useCase`() = runTest {
        // WHEN
        citiesViewModel.getCities()

        // THEN
        verify(getCitiesUseCase).execute()
    }

    @Test
    fun `WHEN call viewModel THEN verify networkMonitor`() {
        verify(networkMonitor).isOnline
    }
}
