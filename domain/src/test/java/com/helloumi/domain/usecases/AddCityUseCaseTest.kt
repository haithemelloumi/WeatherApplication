package com.helloumi.domain.usecases

import com.helloumi.domain.repository.CitiesForSearchRepository
import com.helloumi.domain.model.CityForSearchDomain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class AddCityUseCaseTest {

    @org.mockito.Mock
    private lateinit var citiesForSearchRepository: CitiesForSearchRepository

    private lateinit var addCityUseCase: AddCityUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addCityUseCase = AddCityUseCase(
            citiesForSearchRepository
        )
    }

    @Test
    fun `WHEN execute useCase THEN verify repository and assert result`() {

        // GIVEN
        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )
        val resultInsert: Long = 5

        runTest {
            Mockito.`when`(citiesForSearchRepository.insertCity(cityDomain))
                .thenReturn(resultInsert)
        }


        // WHEN
        runTest {
            val result = addCityUseCase(cityDomain)
            // THEN
            assertEquals(resultInsert, result)
        }

        // THEN
        runTest {
            verify(citiesForSearchRepository).insertCity(cityDomain)
        }
    }
}
