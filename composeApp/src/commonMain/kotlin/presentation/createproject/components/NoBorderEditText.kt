package presentation.createproject.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.maxTitleCharsAllowedForProject
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooRed
import presentation.theme.LightAppBarIconsColor
import presentation.theme.getTextColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.baseline_visibility_24
import youdo2.composeapp.generated.resources.baseline_visibility_off_24

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
    showHelperText : Boolean = true,
    showClearTextButtonIcon : Boolean = false,
    maxLines : Int  = 3,
    fontSize : Int = 24,
    labelColor : Color = getTextColor(),
    isPasswordField : Boolean = false
) {

    val transition = rememberInfiniteTransition()
    val keyboardController = LocalSoftwareKeyboardController.current

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

    var passwordVisibility by remember{
        mutableStateOf(false)
    }

    val passwordIcon = if(passwordVisibility) painterResource(Res.drawable.baseline_visibility_24) else painterResource(Res.drawable.baseline_visibility_off_24)

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
            color = labelColor,
            fontSize = 13.sp,
            fontFamily = AlataFontFamily(),
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField(
            value = text,
            singleLine = maxLines == 1,
            onValueChange = {
                if(showHelperText) {
                    if (it.length <= maxTitleCharsAllowedForProject) {
                        updateText(it)
                    }
                }else{
                    updateText(it)
                }
            },
            visualTransformation = if (passwordVisibility || !isPasswordField) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = LightAppBarIconsColor,
                errorIndicatorColor = DoTooRed,
                focusedIndicatorColor = LightAppBarIconsColor
            ),
            placeholder = {
                Text(
                    text = placeHolder,
                    color = LightAppBarIconsColor,
                    fontSize = 20.sp,
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
                color = labelColor,
                fontSize = fontSize.sp,
                fontFamily = AlataFontFamily()
            ),
            maxLines = maxLines,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isNotBlank()) {
                        keyboardController?.hide()
                        onDone()
                    } else {
                        updateText("")
                        showTitleErrorAnimation = true
                    }
                },
                onNext = {
                    nextFieldFocusRequester.requestFocus()
                }
            ),
            trailingIcon = {
                if(showClearTextButtonIcon && text.isNotBlank()){
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "",
                        tint = labelColor,
                        modifier = Modifier
                            .clickable {
                                updateText("")
                            }
                    )
                }
                if(isPasswordField){
                    Icon(
                        passwordIcon,
                        contentDescription = "",
                        tint = labelColor,
                        modifier = Modifier
                            .clickable {
                                passwordVisibility = passwordVisibility.not()
                            }
                    )
                }
            }
        )
        if (showHelperText) {
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
}