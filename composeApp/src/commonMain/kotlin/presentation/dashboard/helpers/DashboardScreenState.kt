package presentation.dashboard.helpers

import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DashboardScreenState(
    val isDrawerOpen: Boolean = false,
    val statusBarColor: Color = Color.White, // Or from your theme
    val scale: Float = 1f,
    val roundness: Dp = 0.dp,
    val offsetX: Dp = 0.dp
)

val dashboardScreenStateConverter = TwoWayConverter<DashboardScreenState, AnimationVector4D>(
    convertToVector = { state ->
        AnimationVector4D(
            state.offsetX.value,
            state.roundness.value,
            state.scale,
            state.statusBarColor.toArgb().toFloat()
        )
    },
    convertFromVector = { vector ->
        DashboardScreenState(
            offsetX = vector.v1.dp,
            roundness = vector.v2.dp,
            scale = vector.v3,
            statusBarColor = Color(vector.v4.toInt())
        )
    }
)