package presentation.login.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import presentation.shared.fonts.CantarellFontFamily
import presentation.theme.LightDotooLightBlue
import presentation.theme.NightDotooLightBlue
import presentation.theme.getTextColor


@Composable
fun SignupLineView(
    modifier: Modifier,
    navigateToSignup : () -> Unit,
    clickableTextColor : Color = if (isSystemInDarkTheme()) NightDotooLightBlue else LightDotooLightBlue
) {
    val initialText = "Do not have an account? "
    val signupText = "Signup here"
    val dot = "."

    val normalStyle = SpanStyle(
        fontFamily = CantarellFontFamily(),
        fontSize = 13.sp,
        color = getTextColor()
    )

    val clickableStyle = SpanStyle(
        color = clickableTextColor,
        fontFamily = CantarellFontFamily(),
        fontSize = 15.sp
    )

    val annotatedString = buildAnnotatedString {
        withStyle(style = normalStyle){
            append(initialText)
        }
        withStyle(style = clickableStyle){
            pushStringAnnotation(tag = signupText, annotation = signupText)
            append(signupText)
        }
        withStyle(style = normalStyle){
            append(dot)
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            textAlign = TextAlign.Center),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        onClick= {offset ->
            annotatedString.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.also { span ->
                        when(span.item){
                            signupText ->{
                                navigateToSignup()
                            }
                        }
                }
        }
    )
}
