package com.helloumi.data.di

import com.helloumi.data.database.WeatherDatabase
import com.helloumi.data.datasource.CitiesForSearchDao
import com.helloumi.data.datasource.CurrentWeatherDao
import com.helloumi.data.datasource.ForecastDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideCitiesForSearchDao(weatherDatabase: WeatherDatabase): CitiesForSearchDao =
        weatherDatabase.citiesForSearchDao()

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(weatherDatabase: WeatherDatabase): CurrentWeatherDao =
        weatherDatabase.currentWeatherDao()

    @Provides
    @Singleton
    fun provideForecastDao(weatherDatabase: WeatherDatabase): ForecastDao =
        weatherDatabase.forecastDao()
}
