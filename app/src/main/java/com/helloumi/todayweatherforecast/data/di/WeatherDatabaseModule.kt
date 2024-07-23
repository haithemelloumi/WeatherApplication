package com.helloumi.todayweatherforecast.data.di

import android.content.Context
import com.helloumi.todayweatherforecast.data.database.WeatherDatabase
import com.helloumi.todayweatherforecast.data.database.WeatherDatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDatabaseModule {

    @Singleton
    @Provides
    fun provideCarPageDatabase(@ApplicationContext context: Context, factory: WeatherDatabaseFactory): WeatherDatabase {
        return WeatherDatabase.getInstance(context, factory)
    }
}
