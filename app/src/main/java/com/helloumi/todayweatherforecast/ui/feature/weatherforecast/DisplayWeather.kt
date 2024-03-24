package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import android.content.Context
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.helloumi.todayweatherforecast.domain.model.result.CurrentWeatherResult
import com.helloumi.todayweatherforecast.ui.theme.Dimens
import com.helloumi.todayweatherforecast.ui.theme.WHITE
import com.helloumi.todayweatherforecast.utils.extensions.resIdByName

@Composable
fun DisplayWeather(
    context: Context,
    currentWeatherValue: CurrentWeatherResult.Success,
    noDataLabel: String
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
                .background(currentWeatherValue.currentWeatherResponse.getColor())
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center,
        )
        {
            Image(
                modifier = Modifier.size(Dimens.CURRENT_WEATHER_HEIGHT),
                painter = painterResource(
                    id = getWeatherIconResId(
                        context,
                        currentWeatherValue
                    )
                ),
                contentDescription = null,
            )

            Text(
                text = if (currentWeatherValue.currentWeatherResponse.main != null)
                    currentWeatherValue.currentWeatherResponse.main.getTempString()
                else noDataLabel,
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
                text = currentWeatherValue.currentWeatherResponse.weather?.get(0)?.description
                    ?: noDataLabel,
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

private fun getWeatherIconResId(
    context: Context,
    currentWeatherResult: CurrentWeatherResult.Success
) = context.resIdByName(
    "icon_${currentWeatherResult.currentWeatherResponse.weather?.get(0)?.icon}",
    "drawable"
)