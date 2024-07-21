package presentation.shared.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.Rubik_Black
import youdo2.composeapp.generated.resources.Rubik_BlackItalic
import youdo2.composeapp.generated.resources.Rubik_Bold
import youdo2.composeapp.generated.resources.Rubik_BoldItalic
import youdo2.composeapp.generated.resources.Rubik_ExtraBold
import youdo2.composeapp.generated.resources.Rubik_ExtraBoldItalic
import youdo2.composeapp.generated.resources.Rubik_Italic
import youdo2.composeapp.generated.resources.Rubik_Light
import youdo2.composeapp.generated.resources.Rubik_LightItalic
import youdo2.composeapp.generated.resources.Rubik_Medium
import youdo2.composeapp.generated.resources.Rubik_MediumItalic
import youdo2.composeapp.generated.resources.Rubik_Regular
import youdo2.composeapp.generated.resources.Rubik_SemiBold
import youdo2.composeapp.generated.resources.Rubik_SemiBoldItalic


@Composable
fun RubikFontFamily() = FontFamily(
    listOf(
        Font(resource = Res.font.Rubik_Regular, weight = FontWeight.Normal),
        Font(resource = Res.font.Rubik_Medium, weight = FontWeight.Medium),
        Font(
            resource = Res.font.Rubik_MediumItalic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(resource = Res.font.Rubik_Black, weight = FontWeight.Black),
        Font(
            resource = Res.font.Rubik_BlackItalic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        Font(resource = Res.font.Rubik_Bold, weight = FontWeight.Bold),
        Font(
            resource = Res.font.Rubik_BoldItalic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
        Font(resource = Res.font.Rubik_ExtraBold, weight = FontWeight.ExtraBold),
        Font(
            resource = Res.font.Rubik_ExtraBoldItalic,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),
        Font(
            resource = Res.font.Rubik_Italic,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        ),
        Font(resource = Res.font.Rubik_Light, weight = FontWeight.Light),
        Font(
            resource = Res.font.Rubik_LightItalic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        Font(resource = Res.font.Rubik_SemiBold, weight = FontWeight.SemiBold),
        Font(
            resource = Res.font.Rubik_SemiBoldItalic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        )
    )
)