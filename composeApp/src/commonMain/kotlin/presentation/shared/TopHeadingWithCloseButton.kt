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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor
import presentation.theme.NightDotooTextColor
import presentation.theme.getTextColor

@ExperimentalResourceApi
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
            .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Text(
            text = heading,
            modifier = Modifier
                .weight(1f),
            fontFamily = AlataFontFamily(),
            fontSize = 20.sp,
            color = getTextColor()
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .padding(end = 5.dp)
                .height(30.dp)
                .width(30.dp)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Button to close side drawer.",
                tint = Color.White,
                modifier = Modifier.width(20.dp).height(20.dp)
            )
        }
    }
}