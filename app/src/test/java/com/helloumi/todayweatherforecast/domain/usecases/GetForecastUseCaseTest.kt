package com.helloumi.todayweatherforecast.domain.usecases

import com.helloumi.todayweatherforecast.data.repository.ApcRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetForecastUseCaseTest {

    @Mock
    private lateinit var apcRepositoryImpl: ApcRepositoryImpl

    private lateinit var getForecastUseCase: GetForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getForecastUseCase = GetForecastUseCase(apcRepositoryImpl)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository`() {
        // WHEN
        getForecastUseCase.execute("cityName")

        // THEN
        verify(apcRepositoryImpl).getForecastByCityName("cityName")
    }
}
