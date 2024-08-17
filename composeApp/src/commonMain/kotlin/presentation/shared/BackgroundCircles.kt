package presentation.shared

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import presentation.theme.NightTransparentWhiteColor

@Composable
fun BackgroundCircles(
    navController: NavController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val offsetX1 = remember { Animatable(40f) }
    val offsetX2 = remember { Animatable(20f) }
    val offsetX3 = remember { Animatable(240f) }
    LaunchedEffect(key1 = currentBackStackEntry) {
        if (currentBackStackEntry != null  && currentBackStackEntry?.savedStateHandle?.contains("popBackStack") == false) {
            // Move circles to the right
            launch {
                offsetX1.animateTo(
                    targetValue = offsetX1.value + 50,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                offsetX2.animateTo(
                    targetValue = offsetX2.value + 50,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                offsetX3.animateTo(
                    targetValue = offsetX3.value + 50,
                    animationSpec = tween(durationMillis = 500)
                )
            }
        }else if(currentBackStackEntry != null  && currentBackStackEntry?.savedStateHandle?.contains("popBackStack") == true){
            // Move circles to the left
            launch {
                offsetX1.animateTo(
                    targetValue = offsetX1.value - 50,
                    animationSpec = tween(durationMillis = 500)
                )}
            launch {
                offsetX2.animateTo(
                    targetValue = offsetX2.value - 50,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            launch {
                offsetX3.animateTo(
                    targetValue = offsetX3.value - 50,
                    animationSpec = tween(durationMillis = 500)
                )
            }
            currentBackStackEntry?.savedStateHandle?.remove<Boolean>("popBackStack")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxWidth(), onDraw = {
            drawCircle(
                color = NightTransparentWhiteColor,
                radius = 230.dp.toPx(),
                center = Offset(
                    x = offsetX1.value,
                    y = 100.dp.toPx()
                )
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.3F),
                radius = 100.dp.toPx(),
                center = Offset(
                    x = offsetX2.value,
                    y = 100.dp.toPx()
                )
            )
            drawCircle(
                color = NightTransparentWhiteColor,
                radius = 230.dp.toPx(),
                center = Offset(
                    x = offsetX3.value,
                    y = 550.dp.toPx()
                )
            )
        })
    }
}