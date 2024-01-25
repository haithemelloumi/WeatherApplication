package com.helloumi.weatherapplication.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [WeatherDatabaseFactoryModule::class])
@InstallIn(SingletonComponent::class)
class AppModule