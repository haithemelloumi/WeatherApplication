package com.helloumi.weatherapplication.data.repository

import com.helloumi.weatherapplication.data.datasource.CitiesForSearchDao
import com.helloumi.weatherapplication.data.entity.CityForSearchEntity
import com.helloumi.weatherapplication.data.mapper.CityForSearchDomainMapper
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class CitiesForSearchRepositoryImplTest {


    @Mock
    private lateinit var citiesForSearchDao: CitiesForSearchDao

    @Mock
    private lateinit var cityForSearchDomainMapper: CityForSearchDomainMapper

    @InjectMocks
    lateinit var citiesForSearchRepositoryImpl: CitiesForSearchRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN call insertCity THEN ensure result`() = runTest {
        // GIVEN
        val cityEntity = CityForSearchEntity(
            cityId = "cityId",
            cityName = "cityName"
        )
        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )

        Mockito.`when`(cityForSearchDomainMapper.toCityEntity(cityDomain)).thenReturn(cityEntity)

        // WHEN
        citiesForSearchRepositoryImpl.insertCity(cityDomain)

        // THEN
        verify(cityForSearchDomainMapper).toCityEntity(cityDomain)
        verify(citiesForSearchDao).insertElement(cityEntity)
    }
}
