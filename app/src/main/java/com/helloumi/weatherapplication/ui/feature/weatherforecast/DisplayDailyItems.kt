package com.helloumi.weatherapplication.ui.feature.weatherforecast

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
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.domain.model.result.CurrentWeatherResult
import com.helloumi.weatherapplication.ui.feature.weatherforecast.model.WeatherForecastUiModel
import com.helloumi.weatherapplication.ui.theme.Dimens

@Composable
fun DisplayDailyItems(
    uiModel: WeatherForecastUiModel, currentWeatherValue: CurrentWeatherResult.Success
) {
    Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.INLINE_SM, end = Dimens.INLINE_SM)
        ) {
            DailyItem(
                R.drawable.temperature,
                if (currentWeatherValue.currentWeatherResponse.main != null) currentWeatherValue.currentWeatherResponse.main.feelsLike.toString()
                else uiModel.noDataLabel,
                uiModel.feelsLikeLabel
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.visibility,
                if (currentWeatherValue.currentWeatherResponse.visibility != null) currentWeatherValue.currentWeatherResponse.visibility.toString()
                else uiModel.noDataLabel,
                uiModel.visibilityLabel
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.humidity,
                currentWeatherValue.currentWeatherResponse.main?.getHumidityString()
                    ?: uiModel.noDataLabel,
                uiModel.humidityLabel
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.win_speed,
                currentWeatherValue.currentWeatherResponse.wind?.speed?.toString()
                    ?: uiModel.noDataLabel,
                uiModel.windSpeed
            )
            Spacer(modifier = Modifier.size(Dimens.STACK_SM))

            DailyItem(
                R.drawable.air_pressure,
                if (currentWeatherValue.currentWeatherResponse.main != null)
                    currentWeatherValue.currentWeatherResponse.main.pressure.toString()
                else uiModel.noDataLabel,
                uiModel.airPressureLabel
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
            contentDescription = stringResource(R.string.app_name),
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