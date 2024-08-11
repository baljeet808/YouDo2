package presentation.drawer.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import presentation.theme.NightDotooBrightPink
import presentation.theme.getLightThemeColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon

/**
 * User Profile with Progress bar for total tasks completed
 * **/
@Composable
fun  ProfilePictureView(
    profilePictureUrl : String = "",
    openProfile: () -> Unit,
    progress: Float
) {
    val animatedProgress = animateFloatAsState(
        targetValue = (progress),
        animationSpec = tween(
            delayMillis = 1500,
            durationMillis = 1500,
            easing = LinearEasing
        ), label = ""
    ).value
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clickable(
                onClick = openProfile
            )
            .padding(0.dp), contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(Res.drawable.app_icon),
            contentDescription = "avatarImage",
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(shape = RoundedCornerShape(80.dp))
        )

        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .progressSemantics()
                .size(100.dp),
            color = NightDotooBrightPink,
            trackColor = getLightThemeColor(),
        )
    }
}