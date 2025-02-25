package com.helloumi.domain.usecases

import com.helloumi.domain.repository.ApcRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetForecastUseCaseTest {

    @Mock
    private lateinit var apcRepository: ApcRepository

    private lateinit var getForecastUseCase: GetForecastUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getForecastUseCase = GetForecastUseCase(apcRepository)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository`() {
        // WHEN
        getForecastUseCase.execute("cityName")

        // THEN
        verify(apcRepository).getForecastByCityName("cityName")
    }
}
