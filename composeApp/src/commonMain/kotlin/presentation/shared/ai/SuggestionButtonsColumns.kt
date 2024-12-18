package presentation.shared.ai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun SuggestionButtonsColumn(
    suggestions: List<String>,
    onSuggestionClicked: (String) -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier,
    columnBackgroundColor: Color = Color.White.copy(alpha = 0.2f),
    suggestionButtonBackgroundColor: Color = Color.White,
    suggestionTextAnimateColorTo: Color = Color(4294930718),
    suggestionTextAnimateColorFrom: Color = Color.Black,
    skipButtonBackgroundColor: Color = Color.Black,
    skipButtonTextColor: Color = Color.White,
    sideIconColor: Color = Color.White,
    sideIconPainterRes: DrawableResource = Res.drawable.baseline_auto_awesome_24,
    textAnimationSpeed: Float = 0.2f,
    delayBetweenChars: Long = 50
) {
    val closingScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    val iconOnlyWidth = 40.dp
    val animatedWidth by animateDpAsState(targetValue = if (expanded) 0.dp else iconOnlyWidth)

    LaunchedEffect(Unit) {
        delay(1500)
        expanded = true
    }

    Column(
        modifier = modifier
            .then(if (expanded) Modifier.fillMaxWidth() else Modifier.width(animatedWidth))
            .background(color = columnBackgroundColor, shape = RoundedCornerShape(15.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(sideIconPainterRes),
                contentDescription = "Suggestion icon",
                modifier = Modifier.size(24.dp),
                tint = sideIconColor
            )
        }
        AnimatedVisibility(visible = expanded) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(suggestions) { suggestion ->
                    var animatedProgress by remember { mutableStateOf(suggestion.length.toFloat()) }

                    LaunchedEffect(suggestion) {
                        while (animatedProgress >= 0) {
                            animatedProgress -= textAnimationSpeed
                            delay(delayBetweenChars)
                        }
                    }

                    val animatedText = buildAnnotatedString {
                        val numCharsToColor = animatedProgress.toInt()
                        withStyle(style = SpanStyle(color = suggestionTextAnimateColorTo)) {
                            append(
                                suggestion.substring(
                                    0,
                                    (suggestion.length - numCharsToColor).coerceAtLeast(0)
                                )
                            )
                        }
                        withStyle(style = SpanStyle(color = suggestionTextAnimateColorFrom)) {
                            append(
                                suggestion.substring(
                                    (suggestion.length - numCharsToColor).coerceAtLeast(
                                        0
                                    )
                                )
                            )
                        }
                    }

                    Button(
                        onClick = { onSuggestionClicked(suggestion) },
                        modifier = Modifier.height(34.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = suggestionButtonBackgroundColor)
                    ) {
                        Text(
                            animatedText,
                            fontSize = 14.sp,
                            fontFamily = AlataFontFamily()
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = expanded) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        closingScope.launch {
                            expanded = false
                            delay(1500)
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
}
