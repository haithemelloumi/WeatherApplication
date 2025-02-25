package com.helloumi.ui.features.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.domain.model.CityForSearchDomain
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.Dimens.ITEM_HEIGHT
import com.helloumi.ui.theme.PURPLE_GREY_40


@Composable
fun CityItem(
    city: CityForSearchDomain,
    onClickCity: (CityForSearchDomain) -> Unit,
    onDeleteCity: (CityForSearchDomain) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_SMALL),
        colors = CardDefaults.cardColors(
            containerColor = PURPLE_GREY_40,
        ),
        onClick = { onClickCity(city) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.INLINE_SM)
        ) {
            Text(
                modifier = Modifier
                    .height(ITEM_HEIGHT)
                    .weight(0.8f)
                    // Center Text Vertically
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = city.name,
                // Center Text Vertically
                color = Color.Black,
                style = TextStyle(
                    fontSize = Dimens.TEXT_SIZE_MEDIUM,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(
                onClick = { onDeleteCity(city) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityItemPreview() {
    val city = CityForSearchDomain("1", "city")
    CityItem(city, {}) {}
}
