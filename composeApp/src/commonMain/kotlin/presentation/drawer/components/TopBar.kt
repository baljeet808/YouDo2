package presentation.drawer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.NightDotooBrightPink
import presentation.theme.getTextColor

@ExperimentalResourceApi
@Composable
fun TopBar(
    onMenuItemClick: () -> Unit,
    modifier: Modifier,
    avatarUrl : String = "",
    userName : String = "",
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CircularPictureViewWithProgress(
                size = 50,
                showProgress = true,
                onClick = {
                    onMenuItemClick()
                },
                avatarUrl = avatarUrl,
                progress = 1f
            )

            Column {
                Text(
                    text = userName,
                    color = getTextColor(),
                    fontFamily = AlataFontFamily(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(0.8f).padding(end = 10.dp)
                )
                Text(
                    text = "Here for fun.",
                    color = getTextColor().copy(alpha = 0.6f),
                    fontFamily = AlataFontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(0.8f).padding(end = 10.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                )
            }

            /**
             * Menu button to open Notifications
             * **/
            IconButton(
                onClick = {
                },
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Menu button to open Chat",
                    tint = LightAppBarIconsColor
                )
                AnimatedVisibility(visible = true) {
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(6.dp)
                            .background(
                                color = NightDotooBrightPink,
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                    Spacer(
                        modifier = Modifier
                            .height(15.dp)
                            .width(14.dp)
                    )
                }
            }
        }
    }
}
