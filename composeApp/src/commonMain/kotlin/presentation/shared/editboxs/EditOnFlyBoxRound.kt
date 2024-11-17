package presentation.shared.editboxs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooRed
import presentation.theme.getDayLightColor

@ExperimentalResourceApi
@Composable
fun EditOnFlyBoxRound(
    modifier: Modifier,
    onSubmit : (String) -> Unit,
    placeholder : String,
    label : String,
    maxCharLength : Int,
    onCancel : () -> Unit,
    themeColor : Color,
    lines: Int
) {

    val focusRequester = remember {
        FocusRequester()
    }
    var text  by remember {
        mutableStateOf(
            TextFieldValue(placeholder)
        )
    }
    text = text.copy(selection = TextRange(text.text.length))
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {


        Spacer(modifier = Modifier.weight(.7f))

        /**
         * Text field
         * **/
        TextField(
            value = text,
            onValueChange = { updatedText ->
                if (updatedText.text.length <= maxCharLength) {
                    text = updatedText
                }
            },
            label = {
                Text(
                    text = label,
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = AlataFontFamily(),
                    letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = AlataFontFamily(),
                letterSpacing = TextUnit(value = 2f, TextUnitType.Sp),
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            minLines = lines,
            maxLines = lines,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                cursorColor = Color.Black,
                errorCursorColor = Color.Red
            ),
            keyboardActions = KeyboardActions {
                focusRequester.requestFocus()
            }
        )

        /**
         * Top row for counter text, save and cancel button
         * **/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(15.dp))
            IconButton(
                onClick = {
                    onSubmit(text.text)
                },
                modifier = Modifier
                    .background(
                        color = getDayLightColor(),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .height(30.dp)
                    .width(30.dp)
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Submit button.",
                    tint = themeColor
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            IconButton(
                onClick = onCancel,
                modifier = Modifier
                    .background(
                        color = getDayLightColor(),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .height(30.dp)
                    .width(30.dp)

            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Cancel Button",
                    tint = themeColor
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${text.text.length}/$maxCharLength",
                color = if (text.text.length >= maxCharLength) {
                    DoTooRed
                } else {
                    Color.White
                },
                fontSize = 13.sp,
                fontFamily = AlataFontFamily(),
                modifier = Modifier.padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.width(15.dp))
        }

        Spacer(modifier = Modifier.weight(1f))
    }

}