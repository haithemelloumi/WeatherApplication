package com.helloumi.todayweatherforecast.ui.feature.addcity

import com.helloumi.todayweatherforecast.common.CoroutinesTestRule
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.usecases.AddCityUseCase
import com.helloumi.todayweatherforecast.ui.utils.dispatchers.DispatcherProviderImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddCityViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var addCityUseCase: AddCityUseCase

    private lateinit var addCityViewModel: AddCityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addCityViewModel = AddCityViewModel(
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            addCityUseCase = addCityUseCase
        )
    }

    @Test
    fun `WHEN call addCity THEN verify useCase`() = runTest {
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
