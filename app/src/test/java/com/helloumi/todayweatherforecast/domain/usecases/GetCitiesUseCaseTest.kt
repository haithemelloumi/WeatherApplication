package com.helloumi.todayweatherforecast.domain.usecases

import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.domain.repository.CitiesForSearchRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class GetCitiesUseCaseTest {

    @Mock
    private lateinit var citiesForSearchRepository: CitiesForSearchRepository

    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getCitiesUseCase = GetCitiesUseCase(citiesForSearchRepository)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository and assert result`() {
        // GIVEN
        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )
        val cities = flowOf(listOf(cityDomain, cityDomain, cityDomain))

        Mockito.`when`(citiesForSearchRepository.getAllCities()).thenReturn(cities)

        // WHEN
        val result = getCitiesUseCase.execute()

        // THEN
        verify(citiesForSearchRepository).getAllCities()
        assertEquals(cities, result)
    }
}