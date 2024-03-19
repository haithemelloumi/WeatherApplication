package com.helloumi.weatherapplication.ui.feature.weatherforecast

import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.domain.model.ListItem
import com.helloumi.weatherapplication.domain.model.result.ForecastResult
import com.helloumi.weatherapplication.ui.theme.Dimens
import com.helloumi.weatherapplication.utils.extensions.resIdByName

@Composable
fun DisplayForecast(context: Context, forecastResultValue: ForecastResult.Success) {
    if (forecastResultValue.forecastResponse.list?.isNotEmpty() == true) {
        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.INLINE_SM, end = Dimens.INLINE_SM)
            ) {
                for (listItem in forecastResultValue.forecastResponse.list) {
                    ForecastItem(context, listItem)
                    Spacer(modifier = Modifier.size(Dimens.STACK_SM))
                }
            }
        }
    }
}

@Composable
fun ForecastItem(context: Context, listItem: ListItem) {
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
