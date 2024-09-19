package com.helloumi.ui.feature.weatherforecast

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.ui.R
import com.helloumi.domain.model.Clouds
import com.helloumi.domain.model.Coord
import com.helloumi.domain.model.Main
import com.helloumi.domain.model.Sys
import com.helloumi.domain.model.WeatherItem
import com.helloumi.domain.model.Wind
import com.helloumi.domain.model.response.CurrentWeatherResponse
import com.helloumi.domain.model.result.CurrentWeatherResult
import com.helloumi.ui.theme.Dimens

@Composable
fun DailyItems(
    currentWeatherValue: CurrentWeatherResult.Success
) {
    Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.INLINE_SM, end = Dimens.INLINE_SM)
        ) {

            val main = currentWeatherValue.currentWeatherResponse.main

            DailyItem(
                R.drawable.temperature,
                if (main != null) main.feelsLike.toString()
                else stringResource(id = R.string.no_data),
                stringResource(id = R.string.feels_like)
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.visibility,
                if (currentWeatherValue.currentWeatherResponse.visibility != null) currentWeatherValue.currentWeatherResponse.visibility.toString()
                else stringResource(id = R.string.no_data),
                stringResource(id = R.string.visibility)
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.humidity,
                main?.getHumidityString() ?: stringResource(id = R.string.no_data),
                stringResource(id = R.string.humidity)
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.win_speed,
                currentWeatherValue.currentWeatherResponse.wind?.speed?.toString()
                    ?: stringResource(id = R.string.no_data),
                stringResource(id = R.string.wind_speed)
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.air_pressure,
                if (main != null)
                    main.pressure.toString()
                else stringResource(id = R.string.no_data),
                stringResource(id = R.string.air_pressure)
            )
        }

    }
}

@Composable
fun DailyItem(icDay: Int, data: String, stringText: String) {
    Card(
        modifier = Modifier.size(Dimens.DAILY_ITEM_SIZE),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_MEDIUM)
    ) {
        Icon(
            painter = painterResource(id = icDay),
            contentDescription = stringText,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(Dimens.DAILY_ITEM_ICON_SIZE)
                .padding(top = Dimens.STACK_SM)
        )
        Spacer(modifier = Modifier.size(Dimens.INLINE_XXS))
        Text(
            text = data,
            style = TextStyle(fontSize = Dimens.TEXT_SIZE_BIG, color = Color.Black),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = Dimens.STACK_XXS)
        )
        Spacer(modifier = Modifier.size(Dimens.INLINE_XXS))
        Text(
            text = stringText,
            style = TextStyle(fontSize = Dimens.TEXT_SIZE_MEDIUM, color = Color.Black),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

    }
}


@Preview
@Composable
fun DailyItemsPreview() {

    val currentWeather: CurrentWeatherResult.Success = CurrentWeatherResult.Success(
        CurrentWeatherResponse(
            visibility = 100,
            timezone = 99,
            main = Main(
                temp = 1000.0,
                tempMin = 1000.0,
                grndLevel = 1000.0,
                tempKf = 1000.0,
                feelsLike = 1000.0,
                humidity = 100,
                pressure = 100.0,
                seaLevel = 100.0,
                tempMax = 100.0
            ),
            clouds = Clouds(all = 100),
            sys = Sys("ff"),
            dt = 1000,
            coord = Coord(100.0, 33.0),
            weather = listOf(
                WeatherItem(
                    icon = "01d.png",
                    description = "rainy",
                    main = "",
                    id = 5
                )
            ),
            name = "name",
            cod = 23,
            id = 11,
            base = "gg",
            wind = Wind(2.0, 4.0)
        )
    )

    DailyItems(currentWeather)
}