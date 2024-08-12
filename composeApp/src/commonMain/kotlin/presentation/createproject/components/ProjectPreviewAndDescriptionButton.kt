package presentation.createproject.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.LightDotooFooterTextColor
import presentation.theme.NightDotooFooterTextColor


@Composable
fun ProjectPreviewAndDescriptionButton(
    onPreviewClicked: () -> Unit = {},
    onkeyBoardController: () -> Unit = {},
    showDescription: Boolean = false,
    clearProjectDescription: () -> Unit = {},
    toggleDescriptionVisibility: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    /**
     * Row for dotoo additional fields
     * **/
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(
            onClick = {
                onPreviewClicked()
            },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    },
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                Icons.Outlined.Menu,
                contentDescription = "Button to see the project preview.",
                tint = LightAppBarIconsColor,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Preview",
                color = LightAppBarIconsColor,
                fontFamily = RobotoFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }

        TextButton(
            onClick = {
                toggleDescriptionVisibility()
                if (showDescription) {
                    onkeyBoardController()
                }else{
                    clearProjectDescription()
                }
            },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (isSystemInDarkTheme()) {
                        NightDotooFooterTextColor
                    } else {
                        LightDotooFooterTextColor
                    },
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                if (showDescription) {
                    Icons.Outlined.Menu
                } else {
                    Icons.AutoMirrored.Outlined.List
                },
                contentDescription = "Button to set add description to project.",
                tint = LightAppBarIconsColor
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (showDescription) {
                    "Clear Description"
                } else {
                    "Add Description"
                },
                color = LightAppBarIconsColor,
                fontFamily = RobotoFontFamily(),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}