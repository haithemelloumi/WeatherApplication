package com.helloumi.todayweatherforecast.ui.utils.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
