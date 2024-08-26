package com.helloumi.ui.feature

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.ui.feature.cities.AddCityButton
import com.helloumi.ui.feature.cities.CitiesScreen
import com.helloumi.ui.feature.cities.CitiesViewModel
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.TodayWeatherForecastTheme
import com.helloumi.ui.utils.dispatchers.DispatcherProviderImpl
import com.helloumi.ui.utils.network.NetworkMonitor
import com.helloumi.ui.utils.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @RelaxedMockK
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
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
    fun testCities_with_empty_list() {
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                CitiesScreen(
                    modifier = Modifier,
                    viewModel = citiesViewModel,
                    onClickAddCityButton = { },
                    onClickCity = { _, _ -> }
                )
            }
        }
        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
    }

    @Test
    fun testCities_with_city() {
        citiesViewModel.citiesUiState.value = listOf(CityForSearchDomain("id", "cityName"))
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                CitiesScreen(
                    modifier = Modifier,
                    viewModel = citiesViewModel,
                    onClickAddCityButton = { },
                    onClickCity = { _, _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
        composeTestRule.onNodeWithText("cityName").assertIsDisplayed()
    }

    @Test
    fun test_AddCityButton_with_not_clicked() {
        var clicked = false
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                AddCityButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD),
                    LocalContext.current,
                    onClick = { clicked = true }
                )
            }
        }

        assert(!clicked)
        composeTestRule.onRoot().performClick()
        assert(clicked)
    }


    @Test
    fun test_AddCityButton_with_clicked() {
        var clicked = true
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                AddCityButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD),
                    LocalContext.current,
                    onClick = { clicked = false }
                )
            }
        }

        assert(clicked)
        composeTestRule.onRoot().performClick()
        assert(!clicked)
    }
}
