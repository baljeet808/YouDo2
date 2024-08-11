package presentation.drawer.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.theme.getLightThemeColor


@Composable
fun IconButtonView(
    onClick: () -> Unit = {},
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack
) {
    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .border(
                width = 1.dp,
                color = getLightThemeColor(),
                shape = RoundedCornerShape(40.dp)
            )

    ) {
        Icon(
            icon,
            contentDescription = "Button icon",
            tint = Color.White
        )
    }
}