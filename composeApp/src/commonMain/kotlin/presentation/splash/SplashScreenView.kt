package presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.EnumProjectColors
import common.getColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon


@ExperimentalResourceApi
@Composable
fun SplashScreenView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = EnumProjectColors.DarkBlack.getColor()
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(Res.drawable.app_icon),
            contentDescription = "App Icon",
        )
    }
}