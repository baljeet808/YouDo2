package presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NextButton(
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            tint = Color.White
        )
    }
}