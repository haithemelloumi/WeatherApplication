package com.helloumi.weatherapplication.di

import com.helloumi.weatherapplication.data.repository.ApcRepositoryImpl
import com.helloumi.weatherapplication.data.repository.CitiesForSearchRepositoryImpl
import com.helloumi.weatherapplication.domain.repository.ApcRepository
import com.helloumi.weatherapplication.domain.repository.CitiesForSearchRepository
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
