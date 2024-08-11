package presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun HoverButton(
    onClick: () -> Unit,
    buttonLabel : String = "Get Started",
    contentAlignment : Alignment = Alignment.BottomEnd,
    icon : ImageVector = Icons.Filled.Face,
    showIcon : Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize().padding(bottom = 20.dp, end = 20.dp, start = 20.dp, top = 20.dp),
        contentAlignment = contentAlignment
    ) {
        ElevatedButton(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .height(44.dp)
                .padding(5.dp),
            contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon button"
                )
                Text(text = buttonLabel)
            }
        }
    }
}