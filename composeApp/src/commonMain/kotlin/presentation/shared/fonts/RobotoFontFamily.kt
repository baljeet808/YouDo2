package presentation.shared.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.Roboto_Black
import youdo2.composeapp.generated.resources.Roboto_BlackItalic
import youdo2.composeapp.generated.resources.Roboto_Bold
import youdo2.composeapp.generated.resources.Roboto_BoldItalic
import youdo2.composeapp.generated.resources.Roboto_Italic
import youdo2.composeapp.generated.resources.Roboto_Light
import youdo2.composeapp.generated.resources.Roboto_LightItalic
import youdo2.composeapp.generated.resources.Roboto_Medium
import youdo2.composeapp.generated.resources.Roboto_MediumItalic
import youdo2.composeapp.generated.resources.Roboto_Regular
import youdo2.composeapp.generated.resources.Roboto_Thin
import youdo2.composeapp.generated.resources.Roboto_ThinItalic

@ExperimentalResourceApi
@Composable
fun RobotoFontFamily() = FontFamily(
    listOf(
    Font(resource = Res.font.Roboto_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.Roboto_Medium, weight =  FontWeight.Medium),
    Font(resource = Res.font.Roboto_MediumItalic, weight =  FontWeight.Medium, style =  FontStyle.Italic),
    Font(resource = Res.font.Roboto_Black, weight =  FontWeight.Black),
    Font(resource = Res.font.Roboto_BlackItalic, weight =  FontWeight.Black, style =  FontStyle.Italic),
    Font(resource = Res.font.Roboto_Bold, weight =  FontWeight.Bold),
    Font(resource = Res.font.Roboto_BoldItalic, weight =  FontWeight.Bold, style =  FontStyle.Italic),
    Font(resource = Res.font.Roboto_Thin, weight =  FontWeight.Light),
    Font(resource = Res.font.Roboto_ThinItalic, weight =  FontWeight.Light, style =  FontStyle.Italic),
    Font(resource = Res.font.Roboto_Italic, weight =  FontWeight.Normal, style =  FontStyle.Italic),
    Font(resource = Res.font.Roboto_Light, weight =  FontWeight.Light),
    Font(resource = Res.font.Roboto_LightItalic, weight =  FontWeight.Light, style =  FontStyle.Italic),
    )
)