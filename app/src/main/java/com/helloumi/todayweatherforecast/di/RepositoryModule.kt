package com.helloumi.todayweatherforecast.di

import com.helloumi.todayweatherforecast.data.repository.ApcRepositoryImpl
import com.helloumi.todayweatherforecast.data.repository.CitiesForSearchRepositoryImpl
import com.helloumi.todayweatherforecast.domain.repository.ApcRepository
import com.helloumi.todayweatherforecast.domain.repository.CitiesForSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCitiesForSearchRepository(
        citiesForSearchRepositoryImpl: CitiesForSearchRepositoryImpl
    ): CitiesForSearchRepository

    @Binds
    @Singleton
    abstract fun bindApcRepository(
        apcRepositoryImpl: ApcRepositoryImpl
    ): ApcRepository
}
