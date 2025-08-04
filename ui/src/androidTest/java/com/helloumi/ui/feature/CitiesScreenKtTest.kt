package com.helloumi.ui.feature

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.domain.usecases.GetCitiesUseCase
import com.helloumi.domain.usecases.RemoveCityUseCase
import com.helloumi.ui.feature.cities.AddCityButton
import com.helloumi.ui.feature.cities.CitiesContent
import com.helloumi.ui.feature.cities.CitiesScreenState
import com.helloumi.ui.feature.cities.CitiesViewModel
import com.helloumi.ui.theme.TodayWeatherForecastTheme
import com.helloumi.ui.utils.dispatchers.DispatcherProviderImpl
import com.helloumi.ui.utils.network.NetworkMonitor
import com.helloumi.ui.utils.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
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
    private lateinit var removeCityUseCase: RemoveCityUseCase

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var testUiState: MutableStateFlow<CitiesScreenState>

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
            removeCityUseCase,
            networkMonitor
        )
        // Initial state
        testUiState = MutableStateFlow(CitiesScreenState())
    }

    @Test
    fun testCities_with_empty_list() {
        val emptyState = CitiesScreenState(isLoadingCities = false, cities = emptyList(), isInternetAvailable = true)
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                CitiesContent(
                    modifier = Modifier,
                    uiState = emptyState,
                    onClickAddCityButton = { },
                    onClickCity = { },
                    onDeleteCity = { }
                )
            }
        }
        composeTestRule.onNodeWithText("No cities to display.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
    }

    @Test
    fun testCities_with_city() {
        val city = CityForSearchDomain("id1", "cityNameTest")
        val stateWithCity = CitiesScreenState(
            isLoadingCities = false,
            cities = listOf(city),
            isInternetAvailable = true
        )

        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                CitiesContent(
                    modifier = Modifier,
                    uiState = stateWithCity,
                    onClickAddCityButton = { },
                    onClickCity = { },
                    onDeleteCity = { },
                )
            }
        }

        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
        composeTestRule.onNodeWithText("cityNameTest").assertIsDisplayed()
    }

    @Test
    fun test_AddCityButton_with_not_clicked() {
        var clicked = false
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                AddCityButton(
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
                    onClick = { clicked = false }
                )
            }
        }

        assert(clicked)
        composeTestRule.onRoot().performClick()
        assert(!clicked)
    }
}
