package presentation.drawer.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.formatNicelyWithoutYear
import common.getCurrentDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.NightDotooBrightPink


@Composable
fun TopBar(
    notificationsState : Boolean,
    onMenuItemClick: () -> Unit,
    onNotificationsClicked: () -> Unit,
    modifier: Modifier,
    avatarUrl : String = ""
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 20.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ProfilePictureView(
                size = 50,
                showProgress = true,
                onClick = {
                    onMenuItemClick()
                },
                avatarUrl = avatarUrl,
                progress = 1f
            )

            Text(
                text = getCurrentDateTime().formatNicelyWithoutYear(),
                color = LightAppBarIconsColor,
                fontFamily = RobotoFontFamily(),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )


            /**
             * Menu button to open Chat
             * **/
            IconButton(
                onClick = {
                    onNotificationsClicked()
                },
                modifier = Modifier
            ) {
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = "Menu button to open Chat",
                    tint = LightAppBarIconsColor
                )
                AnimatedVisibility(visible = notificationsState) {
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(6.dp)
                            .background(
                                color = NightDotooBrightPink,
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                    Spacer(modifier = Modifier
                        .height(15.dp)
                        .width(14.dp))
                }

            }

            /**
             * Menu button to open Notifications
             * **/
            IconButton(
                onClick = {
                    onNotificationsClicked()
                },
                modifier = Modifier
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    contentDescription = "Menu button to open Chat",
                    tint = LightAppBarIconsColor
                )
                AnimatedVisibility(visible = notificationsState) {
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(6.dp)
                            .background(
                                color = NightDotooBrightPink,
                                shape = RoundedCornerShape(5.dp)
                            )
                    )
                    Spacer(modifier = Modifier
                        .height(15.dp)
                        .width(14.dp))
                }

            }

        }

    }


}


@Preview
@Composable
fun PreviewTopAppBar() {
    TopBar(
       modifier = Modifier,
        notificationsState = true,
        onMenuItemClick = {},
        onNotificationsClicked = {},
    )
}
