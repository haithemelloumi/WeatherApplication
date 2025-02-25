package com.helloumi.ui.feature.weatherforecast

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helloumi.domain.model.ListItem
import com.helloumi.domain.model.Rain
import com.helloumi.domain.model.Snow
import com.helloumi.domain.model.WeatherItem
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.utils.date.getColor
import com.helloumi.ui.utils.date.getDateTime
import com.helloumi.ui.utils.date.getDay
import com.helloumi.ui.utils.date.getHourOfDay
import com.helloumi.ui.utils.extensions.resIdByName

@Composable
fun ForecastItem(listItem: ListItem) {
    val context: Context = LocalContext.current
    Card(
        modifier = Modifier
            .height(Dimens.FORECAST_ITEM_HEIGHT)
            .width(Dimens.FORECAST_ITEM_WIDTH),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_MEDIUM),
        colors = CardDefaults.cardColors(
            containerColor = getColor(getDateTime(listItem.dt))
        ),
    ) {
        Spacer(modifier = Modifier.size(Dimens.INLINE_SM))
        Text(
            text = getHourOfDay(listItem.dtTxt),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(Dimens.INLINE_XS))
        Text(
            text = getDay(listItem.dt) ?: "",
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
            contentDescription = "iconForecast",
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