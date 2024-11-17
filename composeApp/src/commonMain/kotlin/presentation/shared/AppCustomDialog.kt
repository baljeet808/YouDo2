package presentation.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.ReenieBeanieFontFamily
import presentation.theme.getLightThemeColor
import presentation.theme.getTextColor

@ExperimentalResourceApi
@Composable
fun AppCustomDialog(
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit),
    title: String,
    description: String,
    topRowIcon: ImageVector,
    confirmButtonText: String = "Understood 👍",
    dismissButtonText: String = "Bullshit 🤬",
    showDismissButton: Boolean = false,
    showCheckbox: Boolean = false,
    onChecked: () -> Unit,
    checkBoxText: String = "Don't ask me next time?",
    modifier: Modifier
) {

    var checked by remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = getLightThemeColor()
            ),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontFamily = ReenieBeanieFontFamily(),
                        fontSize = 18.sp,
                        color = getTextColor(),
                        textAlign = TextAlign.Start,
                        letterSpacing = TextUnit(1f, TextUnitType.Sp)
                    )
                    Icon(
                        topRowIcon, contentDescription = "dialog icon", tint = getTextColor()
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = description,
                    fontFamily = ReenieBeanieFontFamily(),
                    fontSize = 14.sp,
                    color = getTextColor(),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    letterSpacing = TextUnit(1f, TextUnitType.Sp)
                )

                AnimatedVisibility(visible = showCheckbox) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            ,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(checked = checked, onCheckedChange = {
                            checked = it
                            if (it) {
                                onChecked()
                            }
                        })
                        Text(
                            text = checkBoxText,
                            fontFamily = ReenieBeanieFontFamily(),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier,
                            color = getTextColor(),
                            letterSpacing = TextUnit(1f, TextUnitType.Sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    TextButton(
                        onClick = {
                            onConfirm()
                        },
                        modifier = Modifier
                    ) {
                        Text(
                            text = confirmButtonText,
                            fontFamily = ReenieBeanieFontFamily(),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier,
                            letterSpacing = TextUnit(1f, TextUnitType.Sp),
                            color = getTextColor(),
                            textDecoration = TextDecoration.Underline
                        )
                    }

                    if (showDismissButton) {
                        Spacer(modifier = Modifier.width(10.dp))
                        TextButton(
                            onClick = onDismiss
                        ) {
                            Text(
                                text = dismissButtonText,
                                fontFamily = ReenieBeanieFontFamily(),
                                fontSize = 15.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier,
                                letterSpacing = TextUnit(1f, TextUnitType.Sp),
                                color = getTextColor(),
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }

            }
        }
    }
}

