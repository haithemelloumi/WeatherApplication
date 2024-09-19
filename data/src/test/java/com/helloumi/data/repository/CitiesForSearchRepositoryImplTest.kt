package com.helloumi.data.repository

import com.helloumi.data.datasource.CitiesForSearchDao
import com.helloumi.data.entity.CityForSearchEntity
import com.helloumi.data.mapper.CityForSearchDomainMapper
import com.helloumi.domain.model.CityForSearchDomain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

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
    fun `WHEN call insertCity THEN verify mapper and dao and assert result`() {
        runTest {
            // GIVEN
            val cityEntity = CityForSearchEntity(
                cityId = "cityId",
                cityName = "cityName"
            )
            val cityDomain = CityForSearchDomain(
                id = "cityId",
                name = "cityName"
            )
            val resultInsert: Long = 7

            Mockito.`when`(cityForSearchDomainMapper.toCityEntity(cityDomain))
                .thenReturn(cityEntity)
            Mockito.`when`(citiesForSearchDao.insertElement(cityEntity)).thenReturn(resultInsert)

            // WHEN
            val result = citiesForSearchRepositoryImpl.insertCity(cityDomain)

            // THEN
            verify(cityForSearchDomainMapper).toCityEntity(cityDomain)
            verify(citiesForSearchDao).insertElement(cityEntity)
            assertEquals(resultInsert, result)
        }
    }
}
