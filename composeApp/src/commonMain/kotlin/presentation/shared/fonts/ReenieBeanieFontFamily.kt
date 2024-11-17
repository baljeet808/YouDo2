package presentation.shared.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import youdo2.composeapp.generated.resources.ReenieBeanie
import youdo2.composeapp.generated.resources.Res


@ExperimentalResourceApi
@Composable
fun ReenieBeanieFontFamily() = FontFamily(
    Font(Res.font.ReenieBeanie, weight = FontWeight.Normal)
)
