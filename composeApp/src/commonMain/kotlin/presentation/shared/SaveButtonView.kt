package presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getTextColor

@ExperimentalResourceApi
@Composable
fun SaveButtonView(
    containerModifier: Modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
    buttonModifier : Modifier = Modifier,
    label: String = "Save",
    buttonThemeColor: Color = EnumProjectColors.Red.getColor(),
    onClick: () -> Unit = {},
    alignment: Alignment = Alignment.BottomEnd,
    showIcon : Boolean = true,
    icon : ImageVector = Icons.Default.Add,
    fontSize : Int = 24,
    enabled : Boolean = true,
    labelColor : Color = getTextColor()
) {
    Box(
        modifier = containerModifier,
        contentAlignment = alignment
    ) {

        Row(
            modifier = buttonModifier
                .background(
                    color = buttonThemeColor.copy(alpha = if(enabled) 1f else 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                .clickable(
                    onClick = {
                        onClick()
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = labelColor,
                    fontSize = fontSize.sp,
                    fontFamily = AlataFontFamily()
                )
            )
            if(showIcon){
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    icon,
                    contentDescription = "Button Icon",
                    tint = Color.White
                )
            }
        }
    }
}