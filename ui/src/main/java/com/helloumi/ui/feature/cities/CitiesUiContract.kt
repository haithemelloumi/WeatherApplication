package com.helloumi.ui.feature.cities

import com.helloumi.domain.model.CityForSearchDomain

// ---------- UI STATE ----------
data class CitiesScreenState(
    val isLoadingCities: Boolean = true,
    val cities: List<CityForSearchDomain> = emptyList(),
    val isInternetAvailable: Boolean = false
)

// ---------- INTENTS (UI events -> ViewModel) ----------
sealed interface CitiesIntent {
    data object LoadInitialData : CitiesIntent
    data class DeleteCity(val city: CityForSearchDomain) : CitiesIntent
    data class CityTapped(val city: CityForSearchDomain) : CitiesIntent
    data object AddCityButtonTapped : CitiesIntent
}

// ---------- EFFECTS (ViewModel -> UI one-time events) ----------
sealed interface CitiesEffect {
    data class NavigateToCityDetails(
        val city: CityForSearchDomain,
        val isInternetAvailable: Boolean
    ) : CitiesEffect

    data object NavigateToAddCity : CitiesEffect
}
