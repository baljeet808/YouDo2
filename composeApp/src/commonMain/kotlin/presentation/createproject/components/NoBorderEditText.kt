package presentation.createproject.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.maxTitleCharsAllowedForProject
import kotlinx.coroutines.delay
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooRed
import presentation.theme.LightAppBarIconsColor
import presentation.theme.getTextColor

@Composable
fun NoBorderEditText(
    text: String = "",
    updateText: (String) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    maxCharAllowed: Int = 100,
    placeHolder: String = "",
    label: String = "",
    modifier: Modifier = Modifier,
    nextFieldFocusRequester: FocusRequester = FocusRequester(),
    onDone: () -> Unit = {},
) {

    val transition = rememberInfiniteTransition()

    val rotation = transition.animateValue(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100),
            repeatMode = RepeatMode.Reverse
        ),
        typeConverter = Float.VectorConverter, label = ""
    )

    var showTitleErrorAnimation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showTitleErrorAnimation) {
        delay(1000)
        showTitleErrorAnimation = false
    }


    /**
     * Text field for adding title
     * **/
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(
            1.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = LightAppBarIconsColor,
            fontSize = 13.sp,
            fontFamily = AlataFontFamily(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        )
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxTitleCharsAllowedForProject) {
                    updateText(it)
                }
            },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = placeHolder,
                    color = getTextColor(),
                    fontSize = 24.sp,
                    fontFamily = AlataFontFamily(),
                    modifier = Modifier
                        .rotate(
                            if (showTitleErrorAnimation) {
                                rotation.value
                            } else {
                                0f
                            }
                        )
                )
            },
            textStyle = TextStyle(
                color = getTextColor(),
                fontSize = 24.sp,
                fontFamily = AlataFontFamily()
            ),
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isNotBlank()) {
                        onDone()
                    } else {
                        updateText("")
                        showTitleErrorAnimation = true
                    }
                },
                onNext = {
                    nextFieldFocusRequester.requestFocus()
                }
            )
        )
        Text(
            text = "${text.length}/$maxCharAllowed",
            color = if (text.length >= maxCharAllowed) {
                DoTooRed
            } else {
                LightAppBarIconsColor
            },
            fontSize = 13.sp,
            fontFamily = AlataFontFamily(),
            modifier = Modifier.padding(start = 15.dp)
        )
    }
}