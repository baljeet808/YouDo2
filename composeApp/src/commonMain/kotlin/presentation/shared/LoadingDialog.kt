package presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import common.EnumProjectColors
import common.getColor
import common.getRandomLoadingMessage
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.onboarding.components.PageCountIndicatorView
import presentation.shared.fonts.AlataFontFamily

@ExperimentalResourceApi
@Composable
fun LoadingDialog(
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black.copy(alpha = 0.7f)
            ),
        contentAlignment = Alignment.Center
    ){
        val loadingMessage by remember{ mutableStateOf(getRandomLoadingMessage()) }
        var currentDotIndex by remember { mutableStateOf(0) }

        LaunchedEffect(key1 = true) {
            while (true) {
                delay(600)
                currentDotIndex = (currentDotIndex + 1) % 4
            }
        }

        LoadingDialogContent(
            backgroundColor = EnumProjectColors.Blue.getColor(),
            dotNumber = currentDotIndex,
            message = loadingMessage
        )
    }
}

@ExperimentalResourceApi
@Composable
fun LoadingDialogContent(
    backgroundColor: Color,
    dotNumber: Int,
    message: String
) {
    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageCountIndicatorView(
            count = 4,
            currentPage = dotNumber,
            modifier = Modifier,
            dotWidth = 8.dp,
            selectedDotWidth = 20.dp
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            fontFamily = AlataFontFamily(),
            color = Color.White,
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}