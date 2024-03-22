package com.helloumi.weatherapplication.di

import com.helloumi.weatherapplication.data.api.apc.ApcAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApcAPIModule {

    @Provides
    @Singleton
    fun provideApcAPI(retrofit: Retrofit): ApcAPI {
        return retrofit.create(ApcAPI::class.java)
    }
}
