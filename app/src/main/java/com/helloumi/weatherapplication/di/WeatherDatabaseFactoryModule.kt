package com.helloumi.weatherapplication.di

import com.helloumi.weatherapplication.data.database.WeatherDatabaseFactory
import com.helloumi.weatherapplication.data.database.WeatherDatabaseFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherDatabaseFactoryModule {

    @Singleton
    @Binds
    abstract fun bindCarPageDatabaseFactory(factory: WeatherDatabaseFactoryImpl): WeatherDatabaseFactory
}
