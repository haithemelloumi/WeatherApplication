package com.helloumi.todayweatherforecast.data.di

import com.helloumi.todayweatherforecast.data.database.WeatherDatabase
import com.helloumi.todayweatherforecast.data.datasource.CitiesForSearchDao
import com.helloumi.todayweatherforecast.data.datasource.CurrentWeatherDao
import com.helloumi.todayweatherforecast.data.datasource.ForecastDao
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
