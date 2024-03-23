package com.helloumi.weatherapplication.ui.feature.cities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.domain.model.CityForSearchDomain
import com.helloumi.weatherapplication.ui.feature.addcity.AddCityActivity
import com.helloumi.weatherapplication.ui.feature.common.WeatherToolbar
import com.helloumi.weatherapplication.ui.feature.main.MainActivity.Companion.CITY_KEY
import com.helloumi.weatherapplication.ui.feature.navigation.WeatherNavigation
import com.helloumi.weatherapplication.ui.theme.Dimens
import com.helloumi.weatherapplication.ui.theme.Dimens.ITEM_HEIGHT
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_MD
import com.helloumi.weatherapplication.ui.theme.Dimens.STACK_XXL
import com.helloumi.weatherapplication.ui.theme.PURPLE_40
import com.helloumi.weatherapplication.ui.theme.PURPLE_GREY_40
import com.helloumi.weatherapplication.ui.theme.Radius
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme
import com.helloumi.weatherapplication.utils.extensions.displayToast

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun Cities(
    isInternetAvailable: MutableState<Boolean>,
    navController: NavHostController,
    citiesUiState: List<CityForSearchDomain>
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

    Box {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (toolbar, list, button) = createRefs()

            WeatherToolbar(
                toolbarText = context.getString(R.string.cities_title),
                scrollState.firstVisibleItemScrollOffset,
                onBackClick = { },
                isArrowBackVisible = false,
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        bottom.linkTo(list.top)
                    }
            )

            Column(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(toolbar.bottom)
                        bottom.linkTo(button.top)
                    }
                    .fillMaxHeight(0.8f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD)
            ) {
                citiesUiState.forEach { city ->
                    Button(
                        onClick = {
                            onClickCity(
                                context,
                                isInternetAvailable,
                                navController,
                                city
                            )
                        },
                        shape = RoundedCornerShape(Radius.EXTRA_LARGE),
                        colors = ButtonDefaults.buttonColors(containerColor = PURPLE_GREY_40)
                    ) {
                        Text(
                            text = city.name,
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
                    .constrainAs(button) {
                        top.linkTo(list.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(bottom = STACK_XXL)
                    .padding(horizontal = STACK_MD),
                context
            ) { onClickButton(context) }
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
    isInternetAvailable: MutableState<Boolean>,
    navController: NavHostController,
    city: CityForSearchDomain
) {
    if (isInternetAvailable.value) {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            key = CITY_KEY,
            value = city
        )
        navController.navigate(WeatherNavigation.WeatherAndForecast.destination)
    } else {
        context.displayToast(R.string.weather_no_connection)
    }
}

private fun onClickButton(context: Context) {
    val myIntent = Intent(context, AddCityActivity::class.java)
    context.startActivity(myIntent)
}


@Preview(showBackground = true)
@Composable
fun CitiesPreview(
    isInternetAvailable: MutableState<Boolean>,
    navController: NavHostController,
    citiesUiState: List<CityForSearchDomain>
) {
    WeatherApplicationTheme {
        Cities(isInternetAvailable, navController, citiesUiState)
    }
}
