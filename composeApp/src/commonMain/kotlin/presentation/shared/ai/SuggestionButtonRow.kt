package presentation.shared.ai

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_auto_awesome_24

@ExperimentalResourceApi
@Composable
fun SuggestionButtonRow(
    onActionButtonClicked: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier,
    suggestionText : String = "Suggestion",
    textAnimationSpeed : Float = 0.5f,
    delayBetweenChars : Long = 50,
    rowBackgroundColor : Color = Color.White.copy(alpha = 0.2f),
    suggestionButtonBackgroundColor: Color = Color.White,
    suggestionTextAnimateColorTo : Color = Color(4294930718),
    suggestionTextAnimateColorFrom : Color = Color.Black,
    skipButtonBackgroundColor : Color = Color.Black,
    skipButtonTextColor : Color = Color.White,
    sideIconColor : Color = Color.White,
    sideIconPainterRes : DrawableResource = Res.drawable.baseline_auto_awesome_24
) {
    val closingScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val rowWidth by animateDpAsState(targetValue = if (expanded) 300.dp else 40.dp) // Adjust expanded width as needed

    var animatedProgress by remember { mutableStateOf(suggestionText.length.toFloat()) }

    LaunchedEffect(key1 = Unit) {
        delay(1500)
        expanded = true
        while (animatedProgress >= 0) {
            animatedProgress -= textAnimationSpeed // Adjust animation speed
            delay(delayBetweenChars) // Adjust animation delay
        }
    }

    val animatedText = buildAnnotatedString {
        val numCharsToColor = animatedProgress.toInt()
        withStyle(style = SpanStyle(color = suggestionTextAnimateColorTo)) {
            append(suggestionText.substring(0, (suggestionText.length - numCharsToColor).coerceAtLeast(0)))
        }
        withStyle(style = SpanStyle(color = suggestionTextAnimateColorFrom)) {
            append(suggestionText.substring((suggestionText.length - numCharsToColor).coerceAtLeast(0)))
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .width(rowWidth) // Animate width
            .background(
                color = rowBackgroundColor,
                shape = RoundedCornerShape(15.dp)
            ).padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Icon(
            painter = painterResource(sideIconPainterRes),
            contentDescription = "Suggestion icon",
            modifier = Modifier.height(20.dp).width(20.dp),
            tint = sideIconColor
        )

        if(expanded){
            Button(
                onClick = onActionButtonClicked,
                modifier = Modifier.height(34.dp),
                colors = ButtonDefaults.buttonColors(containerColor = suggestionButtonBackgroundColor)
            ) {
                Text(
                    animatedText,
                    fontFamily = AlataFontFamily()
                )
            }

            Button(
                onClick = {
                    closingScope.launch {
                        expanded = false // Collapse the row when Skip is clicked
                        delay(1000)
                        onSkipClick()
                    }
                },
                modifier = Modifier.height(34.dp),
                colors = ButtonDefaults.buttonColors(containerColor = skipButtonBackgroundColor)
            ) {
                Text(
                    "Skip",
                    fontSize = 12.sp,
                    color = skipButtonTextColor,
                    fontFamily = AlataFontFamily()
                )
            }
        }


    }
}