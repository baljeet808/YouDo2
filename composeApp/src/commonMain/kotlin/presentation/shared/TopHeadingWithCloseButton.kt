package presentation.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.NightDotooTextColor
import presentation.theme.getTextColor

@Composable
fun TopHeadingWithCloseButton(
    onClose : () -> Unit = {},
    heading : String,
    modifier: Modifier
) {
    /**
     * Row for top close button
     * **/
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {


        Text(
            text = heading,
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            fontFamily = AlataFontFamily(),
            fontSize = 28.sp,
            color = getTextColor()
        )


        IconButton(
            onClick = onClose,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .border(
                    width = 2.dp,
                    color = if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    },
                    shape = RoundedCornerShape(40.dp)
                )
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Button to close side drawer.",
                tint = if (isSystemInDarkTheme()) {
                    NightDotooTextColor
                } else {
                    Color.Black
                }
            )
        }
    }
}