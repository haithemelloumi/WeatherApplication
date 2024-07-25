package com.helloumi.todayweatherforecast.ui.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
}