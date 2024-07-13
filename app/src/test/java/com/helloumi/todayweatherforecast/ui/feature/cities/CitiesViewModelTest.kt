package com.helloumi.todayweatherforecast.ui.feature.cities

import com.helloumi.todayweatherforecast.common.CoroutinesTestRule
import com.helloumi.todayweatherforecast.domain.usecases.GetCitiesUseCase
import com.helloumi.todayweatherforecast.ui.dispatchers.DispatcherProviderImpl
import com.helloumi.todayweatherforecast.utils.network.NetworkMonitor
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class CitiesViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Mock
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        citiesViewModel = CitiesViewModel(
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            getCitiesUseCase,
            networkMonitor
        )
    }

    @Test
    fun `WHEN call getCities THEN verify useCase`() = runTest {
        // WHEN
        Mockito.`when`(getCitiesUseCase.execute()).thenReturn(
            flow {
                emit(listOf())
            }
        )

        citiesViewModel.getCities()

        // THEN
        verify(getCitiesUseCase).execute()
    }

    @Test
    fun `WHEN call viewModel THEN verify networkMonitor`() {
        verify(networkMonitor).isOnline
    }
}