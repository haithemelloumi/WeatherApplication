package com.helloumi.ui.feature.weatherforecast

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.ui.R
import com.helloumi.domain.model.City
import com.helloumi.domain.model.ListItem
import com.helloumi.domain.model.Rain
import com.helloumi.domain.model.Snow
import com.helloumi.domain.model.WeatherItem
import com.helloumi.domain.model.response.ForecastResponse
import com.helloumi.domain.model.result.ForecastResult
import com.helloumi.ui.theme.Dimens

@Composable
fun Forecast(
    forecastResultValue: ForecastResult.Success
) {
    if (forecastResultValue.forecastResponse.list?.isNotEmpty() == true) {
        LazyRow(
            Modifier
                .fillMaxWidth()
                .padding(start = Dimens.INLINE_SM, end = Dimens.INLINE_SM)
        ) {
            val list: List<ListItem>? = forecastResultValue.forecastResponse.list
            if (list != null) {
                items(list) {
                    ForecastItem(it)
                    Spacer(modifier = Modifier.size(Dimens.STACK_SM))
                }
            }
        }
    } else {
        ErrorMessage(stringResource(id = R.string.no_data))
    }
}

@Preview
@Composable
fun ForecastPreview() {
    val response = ForecastResponse(
        city = City(
            country = null,
            coord = null,
            name = "name",
            id = 2
        ),
        cnt = 3,
        cod = "df",
        message = 23.9,
        list = listOf(
            ListItem(
                dt = 124455L,
                rain = Rain(22.0),
                dtTxt = "dtTxt",
                snow = Snow(22.0),
                weather = listOf(
                    WeatherItem(
                        icon = "01d",
                        description = "description",
                        main = "main",
                        id = 12
                    )
                ),
                main = null,
                clouds = null,
                sys = null,
                wind = null
            )
        )
    )

    Forecast(
        forecastResultValue = ForecastResult.Success(response)
    )
}
