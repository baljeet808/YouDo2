package presentation.onboarding.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PageCountIndicatorView(
    count : Int = 3,
    currentPage : Int = 0,
    modifier: Modifier = Modifier
    .fillMaxWidth(),
    dotWidth: Dp = 15.dp,
    selectedDotWidth: Dp = 40.dp,
    dotColor: Color = Color.White,
    inactiveDotColor: Color = dotColor.copy(alpha = 0.5f)
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until count) {
            key(i) {
                IndicatorDot(
                    isSelected = i == currentPage,
                    dotWidth = dotWidth,
                    selectedDotWidth = selectedDotWidth,
                    dotColor = dotColor,
                    inactiveDotColor = inactiveDotColor
                )
            }
        }
    }
}

@Composable
fun IndicatorDot(
    isSelected: Boolean,
    dotWidth: Dp,
    selectedDotWidth: Dp,
    dotColor: Color,
    inactiveDotColor: Color
) {
    val width = animateDpAsState(
        targetValue = if (isSelected) selectedDotWidth else dotWidth,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .width(width.value)
            .height(dotWidth)
            .background(
                color = if (isSelected) dotColor else inactiveDotColor,
                shape = RoundedCornerShape(20.dp)
            )
    )
}