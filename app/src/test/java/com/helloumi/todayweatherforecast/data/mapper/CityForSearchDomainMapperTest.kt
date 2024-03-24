package com.helloumi.todayweatherforecast.data.mapper

import com.helloumi.todayweatherforecast.data.entity.CityForSearchEntity
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class CityForSearchDomainMapperTest {

    @InjectMocks
    lateinit var cityForSearchDomainMapper: CityForSearchDomainMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN call toCityEntity THEN ensure result`() {
        // GIVEN
        val cityEntity = CityForSearchEntity(
            cityId = "cityId",
            cityName = "cityName"
        )

        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )

        // WHEN
        val result = cityForSearchDomainMapper.toCityEntity(cityDomain)

        // THEN
        assertEquals(cityEntity, result)
    }

    @Test
    fun `WHEN call toCitiesEntities THEN ensure result`() {
        // GIVEN
        val cityEntity = CityForSearchEntity(
            cityId = "cityId",
            cityName = "cityName"
        )
        val cityEntityList = listOf(cityEntity, cityEntity, cityEntity)

        val cityDomain = CityForSearchDomain(
            id = "cityId",
            name = "cityName"
        )
        val cityDomainList = listOf(cityDomain, cityDomain, cityDomain)


        // WHEN
        val result = cityForSearchDomainMapper.toCitiesEntities(cityDomainList)

        // THEN
        assertEquals(cityEntityList, result)
    }
}
