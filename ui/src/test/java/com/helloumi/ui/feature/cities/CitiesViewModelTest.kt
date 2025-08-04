package com.helloumi.ui.feature.cities

import com.helloumi.common.CoroutinesTestRule
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.domain.usecases.RemoveCityUseCase
import com.helloumi.ui.utils.dispatchers.DispatcherProviderImpl
import com.helloumi.ui.utils.network.NetworkMonitor
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class CitiesViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Mock
    private lateinit var removeCityUseCase: RemoveCityUseCase

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
            getCitiesUseCase = getCitiesUseCase,
            removeCityUseCase = removeCityUseCase,
            networkMonitor = networkMonitor
        )
    }

    @Test
    fun `WHEN call loadCities THEN verify useCase`() = runTest {
        // WHEN
        citiesViewModel.loadCities()

        // THEN
        verify(getCitiesUseCase).invoke()
    }

    @Test
    fun `WHEN call observeInternetStatus THEN verify networkMonitor`() {
        citiesViewModel.observeInternetStatus()
        verify(networkMonitor).isOnline
    }

    @Test
    fun `WHEN call deleteCityInternal THEN verify networkMonitor`() {
        val city = CityForSearchDomain("id1", "Paris")
        citiesViewModel.deleteCityInternal(city)
        verify(removeCityUseCase).invoke(city)
    }
}
