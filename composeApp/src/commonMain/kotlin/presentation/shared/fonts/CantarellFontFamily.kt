package presentation.shared.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import youdo2.composeapp.generated.resources.Cantarell_Bold
import youdo2.composeapp.generated.resources.Cantarell_Regular
import youdo2.composeapp.generated.resources.Res


@Composable
fun CantarellFontFamily() = FontFamily(
    Font(Res.font.Cantarell_Regular, weight = FontWeight.Normal),
    Font(Res.font.Cantarell_Bold, weight = FontWeight.Bold),
)