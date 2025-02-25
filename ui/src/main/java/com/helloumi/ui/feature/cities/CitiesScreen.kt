package com.helloumi.ui.feature.cities

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.ui.features.home.CityItem
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.PURPLE_40

/*
Added to display preview because The previews system is not capable
of constructing all of the parameters passed to a ViewModel
*/
@Composable
fun CitiesScreen(
    modifier: Modifier,
    viewModel: CitiesViewModel = hiltViewModel(),
    onClickAddCityButton: () -> Unit,
    onClickCity: (city: CityForSearchDomain, isInternetAvailable: Boolean) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getCities()
        viewModel.collectIsOnline()
    }
    val cities by viewModel.citiesUiState.collectAsStateWithLifecycle()
    val isInternetAvailable by viewModel.isInternetAvailable.collectAsStateWithLifecycle()
    CitiesContent(
        modifier = modifier,
        cities = cities,
        isInternetAvailable = isInternetAvailable,
        onClickAddCityButton = onClickAddCityButton,
        onClickCity = onClickCity,
        onDeleteCity = { city -> viewModel.deleteCity(city) }
    )
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun CitiesContent(
    modifier: Modifier,
    cities: List<CityForSearchDomain>,
    isInternetAvailable: Boolean,
    onClickAddCityButton: () -> Unit,
    onClickCity: (CityForSearchDomain, Boolean) -> Unit,
    onDeleteCity: (CityForSearchDomain) -> Unit
) {

    //LazyList scroll position
    val scrollState = rememberLazyListState()

    //System UI state
    val systemUiController = rememberSystemUiController()

    val isDarkTheme = isSystemInDarkTheme()
    systemUiController.setStatusBarColor(
        color = PURPLE_40,
        darkIcons =
        if (isDarkTheme) false
        else scrollState.firstVisibleItemScrollOffset != 0
    )

    Column(
        modifier = modifier.fillMaxSize(),
    ) {

        LazyColumn(
            modifier = Modifier
                // fill screen
                .weight(1f)
                .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_SM)
        ) {
            items(cities) { city ->
                CityItem(
                    city,
                    { onClickCity(city, isInternetAvailable) }
                ) {
                    onDeleteCity(city)
                }
                Spacer(modifier = Modifier.height(Dimens.STACK_XS))
            }
        }

        AddCityButton { onClickAddCityButton() }
    }

}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CitiesPreview() {
    val cities = listOf(
        CityForSearchDomain("id", "name")
    )
    CitiesContent(Modifier, cities, true, {}, { _, _ -> }) { _ -> }
}
