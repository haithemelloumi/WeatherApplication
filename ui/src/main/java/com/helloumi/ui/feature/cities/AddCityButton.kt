package com.helloumi.ui.feature.cities

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.helloumi.ui.theme.Dimens.ROUNDED_SHAPE_SMALL
import com.helloumi.ui.theme.PURPLE_40
import com.helloumi.ui.R
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.WHITE

@Composable
fun AddCityButton(
    onClick: () -> Unit
) {
    Button(
        modifier =  Modifier
            .fillMaxWidth()
            .height(Dimens.ADD_CITY_BUTTON_HEIGHT)
            .padding(horizontal = Dimens.STACK_MD, vertical = Dimens.STACK_SM),
        colors = ButtonDefaults.buttonColors(containerColor = PURPLE_40),
        shape = RoundedCornerShape(ROUNDED_SHAPE_SMALL),
        onClick = onClick
    ) {
        Text(
            text = stringResource(R.string.add_city_button),
            color = WHITE
        )
    }
}
