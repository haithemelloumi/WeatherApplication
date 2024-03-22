package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.data.repository.ApcRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetCurrentWeatherUseCaseTest {

    @Mock
    private lateinit var apcRepositoryImpl: ApcRepositoryImpl

    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(apcRepositoryImpl)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository`() {
        // WHEN
        getCurrentWeatherUseCase.execute("cityName")

        // THEN
        verify(apcRepositoryImpl).getCurrentWeatherByCityName("cityName")
    }
}
