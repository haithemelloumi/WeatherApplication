package com.helloumi.ui.feature.cities

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.PURPLE_40
import kotlinx.coroutines.flow.collectLatest

/*
Added to display preview because The previews system is not capable
of constructing all of the parameters passed to a ViewModel
*/
@Composable
fun CitiesScreen(
    modifier: Modifier = Modifier,
    viewModel: CitiesViewModel = hiltViewModel(),
    navigateToAddCity: () -> Unit,
    navigateToCityDetails: (city: CityForSearchDomain, isInternetAvailable: Boolean) -> Unit
) {
    // Collect State
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Manage effects (navigation)
    LaunchedEffect(key1 = Unit) { // key1 = Unit pour que ça s'exécute une seule fois à la composition initiale
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CitiesEffect.NavigateToCityDetails -> {
                    navigateToCityDetails(effect.city, effect.isInternetAvailable)
                }

                is CitiesEffect.NavigateToAddCity -> {
                    navigateToAddCity()
                }
            }
        }
    }

    // Send initial intent to load data
    LaunchedEffect(key1 = Unit) {
        viewModel.processIntent(CitiesIntent.LoadInitialData)
    }

    CitiesContent(
        modifier = modifier,
        uiState = uiState,
        onDeleteCity = { city -> viewModel.processIntent(CitiesIntent.DeleteCity(city)) },
        onClickCity = { city -> viewModel.processIntent(CitiesIntent.CityTapped(city)) },
        onClickAddCityButton = { viewModel.processIntent(CitiesIntent.AddCityButtonTapped) }
    )
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun CitiesContent(
    modifier: Modifier,
    uiState: CitiesScreenState,
    onClickAddCityButton: () -> Unit,
    onClickCity: (CityForSearchDomain) -> Unit,
    onDeleteCity: (CityForSearchDomain) -> Unit
) {
    val scrollState = rememberLazyListState()
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    systemUiController.setStatusBarColor(
        color = PURPLE_40,
        darkIcons = if (isDarkTheme) false else scrollState.firstVisibleItemScrollOffset != 0
    )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        // Display Loading State
        if (uiState.isLoadingCities && uiState.cities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = scrollState, // send scroll state
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_SM)
            ) {
                items(
                    uiState.cities,
                    key = { city -> city.id }) { city -> // Ajouter une clé pour de meilleures performances
                    CityItem(
                        city = city,
                        onClickCity = { onClickCity(city) },
                        onDeleteCity = { onDeleteCity(city) }
                    )
                    Spacer(modifier = Modifier.height(Dimens.STACK_XS))
                }
            }
            // Display message when no cities are available
            if (uiState.cities.isEmpty() && !uiState.isLoadingCities) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No cities to display.")
                }
            }
        }

        AddCityButton(onClick = onClickAddCityButton)
    }
}


@Preview(showBackground = true)
@Composable
fun CitiesPreview() {
    val sampleState = CitiesScreenState(
        isLoadingCities = false,
        cities = listOf(
            CityForSearchDomain("id1", "Paris"),
            CityForSearchDomain("id2", "London")
        ),
        isInternetAvailable = true
    )
    CitiesContent(
        modifier = Modifier,
        uiState = sampleState,
        onClickAddCityButton = {},
        onClickCity = { _ -> },
        onDeleteCity = { _ -> }
    )
}
