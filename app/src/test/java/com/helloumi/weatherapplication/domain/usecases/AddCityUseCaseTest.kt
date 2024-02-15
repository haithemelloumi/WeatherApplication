package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class AddCityUseCaseTest {

    @Mock
    private lateinit var citiesForSearchRepository: CitiesForSearchRepository

    private lateinit var addCityUseCase: AddCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addCityUseCase = AddCityUseCase(citiesForSearchRepository)
    }

    @Test
    fun `WHEN execute useCase THEN verify repository and assert result`() = runTest {
        // GIVEN
        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )
        val resultInsert: Long = 5

        Mockito.`when`(citiesForSearchRepository.insertCity(cityDomain)).thenReturn(resultInsert)

        // WHEN
        val result = addCityUseCase.execute(cityDomain)

        // THEN
        verify(citiesForSearchRepository).insertCity(cityDomain)
        assertEquals(resultInsert, result)
    }
}