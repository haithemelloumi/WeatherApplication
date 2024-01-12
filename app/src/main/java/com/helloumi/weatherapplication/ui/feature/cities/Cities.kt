package com.helloumi.weatherapplication.ui.feature.cities

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.ui.feature.composable.WeatherToolbar
import com.helloumi.weatherapplication.ui.theme.Dimens
import com.helloumi.weatherapplication.ui.theme.Purple40
import com.helloumi.weatherapplication.ui.theme.WeatherApplicationTheme

@Composable
fun Cities() {

    //LazyList scroll position
    val scrollState = rememberLazyListState()

    //System UI state
    val systemUiController = rememberSystemUiController()

    val isDarkTheme = isSystemInDarkTheme()
    systemUiController.setStatusBarColor(
        color = Purple40,
        darkIcons =
        if (isDarkTheme) false
        else scrollState.firstVisibleItemScrollOffset != 0
    )

    val context: Context = LocalContext.current

    Box {
        WeatherToolbar(
            toolbarText = context.getString(R.string.cities_toolbar),
            onBackClick = { },
            isArrowBackVisible = false
        )
        Column(
            modifier = Modifier
                .fillMaxSize()  // Add fill max size
                .verticalScroll(rememberScrollState())
        ) {

            // Spacer to fill up the available space
            Spacer(modifier = Modifier.weight(1f))

            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_MD),
                onClick = {
                    //your onclick code here
                }) {
                Text(text = context.getString(R.string.add_city_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherApplicationTheme {
        Cities()
    }
}