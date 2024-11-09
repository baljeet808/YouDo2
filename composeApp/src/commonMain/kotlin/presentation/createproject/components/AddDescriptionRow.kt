package presentation.createproject.components

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
import org.jetbrains.compose.resources.painterResource
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_auto_awesome_24

@Composable
fun SuggestionButtonRow(
    onActionButtonClicked: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier,
    suggestionText : String = "Suggestion"
) {

    var animatedProgress by remember { mutableStateOf(1f) }
    var isAnimatingForward by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            if (isAnimatingForward) {
                if (animatedProgress < suggestionText.length) {
                    animatedProgress += 0.5f // Adjust animation speed
                    delay(50) // Adjust animation delay
                } else {
                    isAnimatingForward = false
                    delay(5000) // Delay before reversing
                }
            } else {
                if (animatedProgress > 0) {
                    animatedProgress -= 0.5f // Adjust animation speed
                    delay(50) // Adjust animation delay
                } else {
                    isAnimatingForward = true
                    delay(5000) // Delay before restarting
                }
            }
        }
    }

    val animatedText = buildAnnotatedString {
        val numCharsToColor = animatedProgress.toInt()
        withStyle(style = SpanStyle(color =Color(4294930718))) {
            append(suggestionText.substring(0, (suggestionText.length - numCharsToColor).coerceAtLeast(0)))
        }
        withStyle(style = SpanStyle(color = Color.Black)) {
            append(suggestionText.substring((suggestionText.length - numCharsToColor).coerceAtLeast(0)))
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.then(Modifier.fillMaxWidth())
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(15.dp)
            ).padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,

    ) {

        Icon(
            painter = painterResource(Res.drawable.baseline_auto_awesome_24),
            contentDescription = "Suggestion icon",
            modifier = Modifier.height(20.dp).width(20.dp),
            tint = Color.White
        )

        Button(
            onClick = onActionButtonClicked,
            modifier = Modifier.height(34.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(animatedText)
        }


        Button(
            onClick = onSkipClick,
            modifier = Modifier.height(34.dp),
        ) {
            Text("Skip", fontSize = 12.sp)
        }
    }
}