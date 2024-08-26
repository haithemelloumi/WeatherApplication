package com.helloumi.ui.feature.weatherforecast

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.domain.model.Clouds
import com.helloumi.domain.model.Coord
import com.helloumi.domain.model.Main
import com.helloumi.domain.model.Sys
import com.helloumi.domain.model.WeatherItem
import com.helloumi.domain.model.Wind
import com.helloumi.domain.model.response.CurrentWeatherResponse
import com.helloumi.domain.model.result.CurrentWeatherResult
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.PINK_40
import com.helloumi.ui.theme.WHITE
import com.helloumi.ui.utils.extensions.resIdByName
import com.helloumi.ui.R
import com.helloumi.ui.utils.date.getColor
import com.helloumi.ui.utils.date.getDateTime

/*
Added to display preview and ignore currentWeatherResult.currentWeatherResponse.weather[0] problem
*/
@Composable
fun Weather(
    currentWeatherValue: CurrentWeatherResult.Success,
    noDataLabel: String,
) {
    val weatherIcon = getWeatherIconResId(LocalContext.current, currentWeatherValue)
    Weather(
        currentWeatherValue = currentWeatherValue,
        noDataLabel = noDataLabel,
        weatherIcon = weatherIcon,
    )
}

@Composable
fun Weather(
    currentWeatherValue: CurrentWeatherResult.Success,
    noDataLabel: String,
    @DrawableRes weatherIcon: Int,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.INLINE_SM),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_MEDIUM),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.CURRENT_WEATHER_HEIGHT)
                .background(
                    getColor(
                        getDateTime(currentWeatherValue.currentWeatherResponse.dt)
                    )
                )
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
        )
        {
            Image(
                modifier = Modifier.size(Dimens.CURRENT_WEATHER_HEIGHT),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
            )

            val main = currentWeatherValue.currentWeatherResponse.main
            Text(
                text = if (main != null)
                    main.getTempString()
                else "noDataLabel",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.TEXT_SIZE_EXTREMELY_BIG,
                    color = WHITE
                )
            )

            if (currentWeatherValue.currentWeatherResponse.main != null) {
                Text(
                    text = "°C",
                    modifier = Modifier.padding(top = Dimens.STACK_XXL),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = Dimens.TEXT_SIZE_VERY_BIG,
                        color = WHITE
                    )
                )
            }

            Text(
                text = currentWeatherValue.currentWeatherResponse.getDescription() ?: noDataLabel,
                modifier = Modifier
                    .padding(start = Dimens.STACK_SM, end = Dimens.STACK_XS)
                    .align(Alignment.CenterVertically),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.TEXT_SIZE_VERY_BIG,
                    color = WHITE
                )
            )

        }

    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun WeatherPreview() {
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

    Weather(
        currentWeather,
        "noLabel",
        R.drawable.icon_01d
    )
}


private fun getWeatherIconResId(
    context: Context,
    currentWeatherResult: CurrentWeatherResult.Success
): Int {
    val weather = currentWeatherResult.currentWeatherResponse.weather
    return if (!weather.isNullOrEmpty()
        && weather[0] != null
        && weather[0]?.icon != null
    )
        context.resIdByName(
            "icon_${weather[0]?.icon}",
            "drawable"
        )
    else R.drawable.ic_launcher_background
}
