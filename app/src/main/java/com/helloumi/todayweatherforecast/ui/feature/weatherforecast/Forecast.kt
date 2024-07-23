package com.helloumi.todayweatherforecast.ui.feature.weatherforecast

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helloumi.todayweatherforecast.R
import com.helloumi.todayweatherforecast.domain.model.City
import com.helloumi.todayweatherforecast.domain.model.ListItem
import com.helloumi.todayweatherforecast.domain.model.Rain
import com.helloumi.todayweatherforecast.domain.model.Snow
import com.helloumi.todayweatherforecast.domain.model.WeatherItem
import com.helloumi.todayweatherforecast.domain.model.response.ForecastResponse
import com.helloumi.todayweatherforecast.domain.model.result.ForecastResult
import com.helloumi.todayweatherforecast.ui.theme.Dimens
import com.helloumi.todayweatherforecast.ui.utils.extensions.resIdByName

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
            items(forecastResultValue.forecastResponse.list) {
                ForecastItem(it)
                Spacer(modifier = Modifier.size(Dimens.STACK_SM))
            }
        }
    } else {
        ErrorMessage(stringResource(id = R.string.no_data))
    }
}

@Composable
fun ForecastItem(listItem: ListItem) {
    val context: Context = LocalContext.current
    Card(
        modifier = Modifier
            .height(Dimens.FORECAST_ITEM_HEIGHT)
            .width(Dimens.FORECAST_ITEM_WIDTH),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_MEDIUM),
        colors = CardDefaults.cardColors(
            containerColor = listItem.getColor(),
        ),
    ) {
        Spacer(modifier = Modifier.size(Dimens.INLINE_SM))
        Text(
            text = listItem.getHourOfDay(),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(Dimens.INLINE_XS))
        Text(
            text = listItem.getDay() ?: "",
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Icon(
            painter = painterResource(
                id = context.resIdByName(
                    "icon_" + listItem.getWeatherItem()?.icon,
                    "drawable"
                ),
            ),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(Dimens.FORECAST_ITEM_ICON_SIZE)
        )

        Text(
            text = listItem.main?.getTempStringWithDegree() ?: "",
            style = TextStyle(
                fontSize = Dimens.TEXT_SIZE_TITLE,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(Dimens.INLINE_XXS))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = listItem.main?.getTempMinString() ?: "",
                style = TextStyle(
                    fontSize = Dimens.TEXT_SIZE_BIG,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .weight(0.5f),
                textAlign = TextAlign.Center,
            )
            Text(
                text = listItem.main?.getTempMaxString() ?: "",
                style = TextStyle(
                    fontSize = Dimens.TEXT_SIZE_BIG,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .weight(0.5f),
                textAlign = TextAlign.Center,
            )
        }
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

@Preview
@Composable
fun ForecastItemPreview() {
    ForecastItem(
        listItem = ListItem(
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
}
