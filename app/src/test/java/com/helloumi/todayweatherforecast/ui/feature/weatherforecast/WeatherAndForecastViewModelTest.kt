package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import android.content.Context
import com.helloumi.todayweatherforecast.R
import com.helloumi.todayweatherforecast.common.TestViewModelScopeRule
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
import com.helloumi.todayweatherforecast.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.todayweatherforecast.utils.extensions.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi
class WeatherAndForecastViewModelTest {

    @get:Rule
    val dispatcherRule = TestViewModelScopeRule()

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Mock
    private lateinit var getForecastUseCase: GetForecastUseCase

    @Mock
    private lateinit var dateUtils: DateUtils

    private lateinit var weatherAndForecastViewModel: WeatherAndForecastViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherAndForecastViewModel = WeatherAndForecastViewModel(
            getCurrentWeatherUseCase,
            getForecastUseCase,
            dateUtils
        )
    }

    @Test
    fun `WHEN call getUiModel THEN verify useCase`() = runTest {
        // GIVEN
        val uiModel = WeatherForecastUiModel(
            cityName = "cityName",
            currentDate = "currentDate",
            screenTitle = "screenTitle",
            forecastLabel = "forecastLabel",
            feelsLikeLabel = "feelsLikeLabel",
            visibilityLabel = "visibilityLabel",
            humidityLabel = "humidityLabel",
            windSpeed = "windSpeed",
            airPressureLabel = "airPressureLabel",
            noDataLabel = "noDataLabel",
            serverUnreachableLabel = "serverUnreachableLabel",
            serverErrorLabel = "serverErrorLabel"
        )

        Mockito.`when`(dateUtils.todayDate()).thenReturn("currentDate")
        Mockito.`when`(context.getString(R.string.weather_title)).thenReturn("screenTitle")
        Mockito.`when`(context.getString(R.string.next_5_days)).thenReturn("forecastLabel")
        Mockito.`when`(context.getString(R.string.feels_like)).thenReturn("feelsLikeLabel")
        Mockito.`when`(context.getString(R.string.visibility)).thenReturn("visibilityLabel")
        Mockito.`when`(context.getString(R.string.humidity)).thenReturn("humidityLabel")
        Mockito.`when`(context.getString(R.string.wind_speed)).thenReturn("windSpeed")
        Mockito.`when`(context.getString(R.string.air_pressure)).thenReturn("airPressureLabel")
        Mockito.`when`(context.getString(R.string.no_data)).thenReturn("noDataLabel")
        Mockito.`when`(context.getString(R.string.weather_server_unreachable))
            .thenReturn("serverUnreachableLabel")
        Mockito.`when`(context.getString(R.string.weather_server_error))
            .thenReturn("serverErrorLabel")

        // WHEN
        val result = weatherAndForecastViewModel.getUiModel(context, "cityName")

        // THEN
        assertEquals(uiModel, result)
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
