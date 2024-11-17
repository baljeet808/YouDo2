package presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getTextColor

@ExperimentalResourceApi
@Composable
fun NextButton(
    backgroundColor: Color,
    onClick: () -> Unit,
    label: String = "",
    fontSize : Int = 20,
    contentColor : Color = getTextColor()
) {
    Row(
        modifier = Modifier
            .widthIn(min = 80.dp)
            .height(60.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if(label.isNotEmpty()){
            Text(
                text = label,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = contentColor,
                    fontSize = fontSize.sp,
                    fontFamily = AlataFontFamily()
                ),
                modifier = Modifier
                    .padding(start = 20.dp, end = 5.dp)
            )
        }
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            tint = contentColor
        )
    }
}