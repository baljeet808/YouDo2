package presentation.shared.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_auto_awesome_24

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

    var animatedProgress by remember { mutableStateOf(suggestionText.length.toFloat()) }

    LaunchedEffect(key1 = Unit) {
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
        modifier = modifier.then(Modifier.fillMaxWidth())
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

        Button(
            onClick = onActionButtonClicked,
            modifier = Modifier.height(34.dp),
            colors = ButtonDefaults.buttonColors(containerColor = suggestionButtonBackgroundColor)
        ) {
            Text(animatedText)
        }


        Button(
            onClick = onSkipClick,
            modifier = Modifier.height(34.dp),
            colors = ButtonDefaults.buttonColors(containerColor = skipButtonBackgroundColor)
        ) {
            Text("Skip", fontSize = 12.sp, color = skipButtonTextColor)
        }
    }
}