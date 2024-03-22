package com.helloumi.weatherapplication.ui.feature.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.ui.theme.BLACK
import com.helloumi.weatherapplication.ui.theme.Dimens
import com.helloumi.weatherapplication.ui.theme.Opacity
import com.helloumi.weatherapplication.ui.theme.Opacity.OPAQUE
import com.helloumi.weatherapplication.ui.theme.PURPLE_40
import com.helloumi.weatherapplication.ui.theme.Radius
import com.helloumi.weatherapplication.ui.theme.TRANSPARENT
import com.helloumi.weatherapplication.ui.theme.WHITE


private const val COLOR_ANIMATION_DURATION = 250

@Composable
fun WeatherToolbar(
    toolbarText: String,
    scrollOffset: Int,
    onBackClick: () -> Unit,
    isArrowBackVisible: Boolean = false,
    modifier: Modifier
) {

    val colorState by animateColorAsState(
        targetValue = PURPLE_40,
        animationSpec = tween(
            durationMillis = COLOR_ANIMATION_DURATION,
            easing = LinearEasing
        ),
        label = "toolbar animated color"
    )

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .background(color = colorState)
    ) {
        val (buttonGarage, textModel) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            tint = if (isPageTop(scrollOffset))
                WHITE
            else
                BLACK,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(buttonGarage) {
                    start.linkTo(parent.start, margin = Dimens.INLINE_MD)
                    top.linkTo(parent.top, margin = Dimens.STACK_XL)
                    bottom.linkTo(parent.bottom, margin = Dimens.STACK_SM)
                }
                .background(
                    shape = RoundedCornerShape(Radius.MEDIUM),
                    color = TRANSPARENT
                )
                .padding(Dimens.INLINE_XS)
                .clickable {
                    onBackClick()
                }
                .semantics { testTag = toolbarText }
                .alpha(if (isArrowBackVisible) OPAQUE else Opacity.TRANSPARENT)
        )

        Text(
            text = toolbarText,
            modifier = Modifier.constrainAs(textModel) {
                centerHorizontallyTo(parent)
                top.linkTo(buttonGarage.top)
                bottom.linkTo(buttonGarage.bottom)
            },
            color = WHITE,
            fontSize = Dimens.TEXT_SIZE_BIG
        )
    }
}

@Preview
@Composable
fun PreviewCarPageToolbar(modifier: Modifier, scrollOffset: Int) {
    WeatherToolbar(
        toolbarText = "toolbarText",
        scrollOffset,
        onBackClick = {},
        modifier = modifier
    )
}

private fun isPageTop(scrollOffset: Int) = scrollOffset == 0
