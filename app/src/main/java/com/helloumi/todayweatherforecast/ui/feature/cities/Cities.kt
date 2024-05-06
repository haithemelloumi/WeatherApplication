package com.helloumi.todayweatherforecast.ui.feature.cities

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.todayweatherforecast.R
import com.helloumi.todayweatherforecast.domain.model.CityForSearchDomain
import com.helloumi.todayweatherforecast.ui.feature.common.WeatherToolbar
import com.helloumi.todayweatherforecast.ui.feature.main.MainActivity.Companion.CITY_KEY
import com.helloumi.todayweatherforecast.ui.feature.navigation.WeatherNavigation
import com.helloumi.todayweatherforecast.ui.theme.Dimens
import com.helloumi.todayweatherforecast.ui.theme.Dimens.ITEM_HEIGHT
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_40
import com.helloumi.todayweatherforecast.ui.theme.PURPLE_GREY_40
import com.helloumi.todayweatherforecast.ui.theme.Radius
import com.helloumi.todayweatherforecast.utils.extensions.displayToast

/*
Added to display preview because The previews system is not capable
of constructing all of the parameters passed to a ViewModel
*/
@Composable
fun Cities(
    navController: NavHostController,
    onClickAddCityButton: () -> Unit
) {
    val viewModel = hiltViewModel<CitiesViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getCities()
        viewModel.collectIsOnline()
    }
    val cities = viewModel.citiesUiState.value
    val isInternetAvailable = viewModel.isInternetAvailable.value
    Cities(
        cities = cities,
        isInternetAvailable = isInternetAvailable,
        navController = navController,
        onClickAddCityButton = onClickAddCityButton
    )
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun Cities(
    cities: List<CityForSearchDomain>,
    isInternetAvailable: Boolean,
    navController: NavHostController,
    onClickAddCityButton: () -> Unit
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

    val context: Context = LocalContext.current

    Column(
        Modifier.fillMaxHeight()
    ) {

        WeatherToolbar(
            toolbarText = context.getString(R.string.cities_title),
            scrollState.firstVisibleItemScrollOffset,
            onBackClick = { },
            isArrowBackVisible = false,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                // fill screen
                .weight(1f)
                .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_SM)
        ) {
            items(cities) {
                Button(
                    onClick = {
                        onClickCity(
                            context,
                            isInternetAvailable,
                            navController,
                            it
                        )
                    },
                    shape = RoundedCornerShape(Radius.EXTRA_LARGE),
                    colors = ButtonDefaults.buttonColors(containerColor = PURPLE_GREY_40)
                ) {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ITEM_HEIGHT)
                            // Center Text Vertically
                            .wrapContentHeight(align = Alignment.CenterVertically),
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.STACK_XS))
            }
        }

        AddCityButton(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_SM),
            context
        ) {
            onClickAddCityButton()
        }
    }

}

@Composable
fun AddCityButton(
    modifier: Modifier,
    context: Context,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = PURPLE_40),
        onClick = onClick
    ) {
        Text(text = context.getString(R.string.add_city_button))
    }
}

private fun onClickCity(
    context: Context,
    isInternetAvailable: Boolean,
    navController: NavHostController,
    city: CityForSearchDomain
) {
    if (isInternetAvailable) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = CITY_KEY,
            value = city
        )
        navController.navigate(WeatherNavigation.WeatherAndForecast.destination)
    } else {
        context.displayToast(R.string.weather_no_connection)
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CitiesPreview() {
    val navController = rememberNavController()
    val cities = listOf(
        CityForSearchDomain("id", "name")
    )
    Cities(cities, true, navController) {}
}
