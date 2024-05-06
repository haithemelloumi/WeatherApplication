package com.helloumi.todayweatherforecast.ui.feature.cities

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import com.helloumi.todayweatherforecast.domain.usecases.GetCitiesUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetForecastUseCase
import com.helloumi.todayweatherforecast.ui.theme.Dimens
import com.helloumi.todayweatherforecast.ui.theme.TodayWeatherForecastTheme
import com.helloumi.todayweatherforecast.utils.extensions.DateUtils
import com.helloumi.todayweatherforecast.utils.network.NetworkMonitor
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SuppressLint("UnrememberedMutableState")
class CitiesKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @RelaxedMockK
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @RelaxedMockK
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @RelaxedMockK
    private lateinit var getForecastUseCase: GetForecastUseCase

    @RelaxedMockK
    private lateinit var dateUtils: DateUtils

    @RelaxedMockK
    private lateinit var networkMonitor: NetworkMonitor

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        weatherViewModel = WeatherViewModel(
            getCitiesUseCase,
            getCurrentWeatherUseCase,
            getForecastUseCase,
            dateUtils,
            networkMonitor
        )
    }

    @Test
    fun testCities_with_empty_list() {
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                Cities(
                    isInternetAvailable = mutableStateOf(true),
                    navController = NavHostController(context),
                    onClickAddCityButton = { }
                )
            }
        }
        composeTestRule.onNodeWithText("CITIES LIST").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add city").assertIsDisplayed()
    }

    @Test
    fun testCities_with_city() {
        composeTestRule.setContent {
            TodayWeatherForecastTheme {
                Cities(
                    isInternetAvailable = mutableStateOf(true),
                    navController = NavHostController(context),
                    onClickAddCityButton = { }
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
            TodayWeatherForecastTheme {
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
            TodayWeatherForecastTheme {
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
