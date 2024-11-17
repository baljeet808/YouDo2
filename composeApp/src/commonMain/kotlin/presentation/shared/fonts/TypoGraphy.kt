package presentation.shared.fonts

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
@Composable
fun Typography() = Typography(
    h1 = TextStyle(
        fontFamily = RobotoFontFamily(),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontFamily = RubikFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = RubikFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = RobotoFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)