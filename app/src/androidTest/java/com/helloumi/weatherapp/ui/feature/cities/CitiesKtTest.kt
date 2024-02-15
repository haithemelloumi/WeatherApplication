package com.helloumi.weatherapp.ui.feature.cities

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.usecases.GetCitiesUseCase
import com.helloumi.weatherapplication.ui.feature.cities.AddCityButton
import com.helloumi.weatherapplication.ui.feature.cities.Cities
import com.helloumi.weatherapplication.ui.feature.cities.CitiesViewModel
import com.helloumi.weatherapplication.ui.theme.Dimens
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @RelaxedMockK
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    private lateinit var citiesViewModel: CitiesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        citiesViewModel = CitiesViewModel(getCitiesUseCase)
    }

    @Test
    fun testCities_with_empty_list() {
        composeTestRule.setContent {
            WeatherApplicationTheme {
                Cities(
                    navController = NavHostController(context),
                    citiesUiState = listOf()
                )
            }
        }
        composeTestRule.onNodeWithText("CITIES LIST").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
    }

    @Test
    fun testCities_with_city() {
        composeTestRule.setContent {
            WeatherApplicationTheme {
                Cities(
                    navController = NavHostController(context),
                    citiesUiState = listOf(CityForSearchDomain("id", "cityName"))
                )
            }
        }
        composeTestRule.onNodeWithText("CITIES LIST").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
        composeTestRule.onNodeWithText("cityName").assertIsDisplayed()
    }

    @Test
    fun test_AddCityButton_with_not_clicked() {
        var clicked = false
        composeTestRule.setContent {
            WeatherApplicationTheme {
                AddCityButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD),
                    context,
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
            WeatherApplicationTheme {
                AddCityButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD),
                    context,
                    onClick = { clicked = false }
                )
            }
        }

        assert(clicked)
        composeTestRule.onRoot().performClick()
        assert(!clicked)
    }
}
