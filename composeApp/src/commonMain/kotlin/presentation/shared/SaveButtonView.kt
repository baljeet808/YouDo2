package presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumProjectColors
import common.getColor
import presentation.shared.fonts.RobotoFontFamily

@Composable
fun SaveButtonView(
    modifier: Modifier,
    label: String= "Save",
    buttonThemeColor: Color = EnumProjectColors.Red.name.getColor(),
    onClick: () -> Unit = {}
) {
    /**
     * Save button
     * **/
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {

        Row(
            modifier = Modifier
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(30.dp))
                .background(
                    color = buttonThemeColor,
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                .clickable(
                    onClick = {
                        onClick()
                    }
                )
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Default.ThumbUp,
                contentDescription = "Button Icon",
                tint = Color.White
            )
        }
    }
}