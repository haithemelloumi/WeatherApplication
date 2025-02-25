package com.helloumi.domain.usecases

import com.helloumi.domain.repository.ApcRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetCurrentWeatherUseCaseTest {

    @Mock
    private lateinit var apcRepository: ApcRepository

    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(apcRepository)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository`() {
        // WHEN
        getCurrentWeatherUseCase.execute("cityName")

        // THEN
        verify(apcRepository).getCurrentWeatherByCityName("cityName")
    }
}
