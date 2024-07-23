package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import com.helloumi.todayweatherforecast.common.CoroutinesTestRule
import com.helloumi.todayweatherforecast.domain.model.City
import com.helloumi.todayweatherforecast.domain.model.Clouds
import com.helloumi.todayweatherforecast.domain.model.Coord
import com.helloumi.todayweatherforecast.domain.model.Main
import com.helloumi.todayweatherforecast.domain.model.Sys
import com.helloumi.todayweatherforecast.domain.model.Wind
import com.helloumi.todayweatherforecast.domain.model.response.CurrentWeatherResponse
import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.todayweatherforecast.domain.usecases.GetForecastUseCase
import com.helloumi.todayweatherforecast.ui.utils.dispatchers.DispatcherProviderImpl
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class WeatherAndForecastViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Mock
    private lateinit var getForecastUseCase: GetForecastUseCase

    private lateinit var weatherAndForecastViewModel: WeatherAndForecastViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherAndForecastViewModel = WeatherAndForecastViewModel(
            dispatcherProvider = DispatcherProviderImpl(
                main = coroutinesTestRule.testDispatcher,
                io = coroutinesTestRule.testDispatcher,
            ),
            getCurrentWeatherUseCase = getCurrentWeatherUseCase,
            getForecastUseCase = getForecastUseCase
        )
    }

    @Test
    fun `WHEN call getWeather with ServerUnavailable THEN assert result and verify useCase`() =
        runTest {
            // GIVEN
            val expected = CurrentWeatherResult.ServerUnavailable
            Mockito.`when`(getCurrentWeatherUseCase.execute("cityName"))
                .thenReturn(flowOf(expected))

            // WHEN
            weatherAndForecastViewModel.getWeather("cityName")

            // THEN
            verify(getCurrentWeatherUseCase).execute("cityName")
            assertEquals(expected, weatherAndForecastViewModel.currentWeatherUiState.value)
        }

    @Test
    fun `WHEN call getWeather with ServerError THEN assert result and verify useCase`() = runTest {
        // GIVEN
        val expected = CurrentWeatherResult.ServerError
        Mockito.`when`(getCurrentWeatherUseCase.execute("cityName")).thenReturn(flowOf(expected))

        // WHEN
        weatherAndForecastViewModel.getWeather("cityName")

        // THEN
        verify(getCurrentWeatherUseCase).execute("cityName")
        assertEquals(expected, weatherAndForecastViewModel.currentWeatherUiState.value)
    }

    @Test
    fun `WHEN call getWeather with Success THEN assert result and verify useCase`() {
        // GIVEN
        val response = CurrentWeatherResponse(
            visibility = 100,
            timezone = 99,
            main = Main(
                temp = 1000.0,
                tempMin = 1000.0,
                grndLevel = 1000.0,
                tempKf = 1000.0,
                feelsLike = 1000.0,
                humidity = 100,
                pressure = 100.0,
                seaLevel = 100.0,
                tempMax = 100.0
            ),
            clouds = Clouds(all = 100),
            sys = Sys("ff"),
            dt = 1000,
            coord = Coord(100.0, 33.0),
            weather = listOf(),
            name = "name",
            cod = 23,
            id = 11,
            base = "gg",
            wind = Wind(2.0, 4.0)
        )
        val expected = CurrentWeatherResult.Success(response)
        Mockito.`when`(getCurrentWeatherUseCase.execute("cityName")).thenReturn(flowOf(expected))

        // WHEN
        weatherAndForecastViewModel.getWeather("cityName")

        // THEN
        verify(getCurrentWeatherUseCase).execute("cityName")
        assertEquals(
            expected.currentWeatherResponse,
            (weatherAndForecastViewModel.currentWeatherUiState.value as CurrentWeatherResult.Success).currentWeatherResponse
        )
    }

    @Test
    fun `WHEN call getForecast with ServerUnavailable THEN assert result and verify useCase`() =
        runTest {
            // GIVEN
            val expected = ForecastResult.ServerUnavailable
            Mockito.`when`(getForecastUseCase.execute("cityName"))
                .thenReturn(flowOf(expected))

            // WHEN
            weatherAndForecastViewModel.getForecast("cityName")

            // THEN
            verify(getForecastUseCase).execute("cityName")
            assertEquals(expected, weatherAndForecastViewModel.forecastResponseUiState.value)
        }

    @Test
    fun `WHEN call getForecast with ServerError THEN assert result and verify useCase`() = runTest {
        // GIVEN
        val expected = ForecastResult.ServerError
        Mockito.`when`(getForecastUseCase.execute("cityName")).thenReturn(flowOf(expected))

        // WHEN
        weatherAndForecastViewModel.getForecast("cityName")

        // THEN
        verify(getForecastUseCase).execute("cityName")
        assertEquals(expected, weatherAndForecastViewModel.forecastResponseUiState.value)
    }

    @Test
    fun `WHEN call getForecast with Success THEN assert result and verify useCase`() = runTest {
        // GIVEN
        val response = ForecastResponse(
            city = City(
                country = null,
                coord = null,
                name = "name",
                id = 2
            ),
            cnt = 3,
            cod = "df",
            message = 23.9,
            list = listOf()
        )
        val expected = ForecastResult.Success(response)
        Mockito.`when`(getForecastUseCase.execute("cityName")).thenReturn(flowOf(expected))

        // WHEN
        weatherAndForecastViewModel.getForecast("cityName")

        // THEN
        verify(getForecastUseCase).execute("cityName")
        assertEquals(
            expected.forecastResponse,
            (weatherAndForecastViewModel.forecastResponseUiState.value as ForecastResult.Success).forecastResponse
        )
    }
}
