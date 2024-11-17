package presentation.shared.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import youdo2.composeapp.generated.resources.ReenieBeanie
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.alata


@ExperimentalResourceApi
@Composable
fun AlataFontFamily() = FontFamily(
    listOf(
    Font(Res.font.alata, weight = FontWeight.Thin),
    Font(Res.font.alata, weight = FontWeight.Normal),
    Font(Res.font.alata, weight = FontWeight.Bold),
    Font(Res.font.alata, weight = FontWeight.SemiBold),
    Font(Res.font.alata, weight = FontWeight.ExtraBold),
    )
)
