package presentation.shared

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.theme.NightTransparentWhiteColor

@Composable
fun BackgroundCircles() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxWidth(), onDraw = {
            drawCircle(
                color = NightTransparentWhiteColor,
                radius = 230.dp.toPx(),
                center = Offset(
                    x = 40.dp.toPx(),
                    y = 100.dp.toPx()
                )
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.3F),
                radius = 100.dp.toPx(),
                center = Offset(x = 20.dp.toPx(),
                    y = 100.dp.toPx()
                )
            )
            drawCircle(
                color = NightTransparentWhiteColor,
                radius = 230.dp.toPx(),
                center = Offset(
                    x = 240.dp.toPx(),
                    y = 550.dp.toPx()
                )
            )
        })
    }
}