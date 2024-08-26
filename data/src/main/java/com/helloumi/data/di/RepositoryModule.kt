package com.helloumi.data.di

import com.helloumi.data.api.apc.ApcAPI
import com.helloumi.data.datasource.CitiesForSearchDao
import com.helloumi.data.mapper.CityForSearchDomainMapper
import com.helloumi.data.repository.ApcRepositoryImpl
import com.helloumi.data.repository.CitiesForSearchRepositoryImpl
import com.helloumi.domain.repository.ApcRepository
import com.helloumi.domain.repository.CitiesForSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCitiesForSearchRepository(
        citiesForSearchDao: CitiesForSearchDao,
        cityForSearchDomainMapper: CityForSearchDomainMapper
    ): CitiesForSearchRepository = CitiesForSearchRepositoryImpl(
        citiesForSearchDao,
        cityForSearchDomainMapper
    )

    @Singleton
    @Provides
    fun provideApcRepository(
        apcAPI: ApcAPI
    ): ApcRepository = ApcRepositoryImpl(apcAPI)
}
