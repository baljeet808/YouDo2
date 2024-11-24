package presentation.shared.projectCardWithProfiles.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumRoles
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LessTransparentWhiteColor

@ExperimentalResourceApi
@Composable
fun ProjectTopBar(
    notificationsState : Boolean,
    onNotificationItemClicked: () -> Unit,
    onDeleteItemClicked: () -> Unit,
    onClickInvite: () -> Unit,
    role : EnumRoles,
    modifier: Modifier
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            /**
             * Role
             * **/
            Text(
                text = "You are ".plus(role.name),
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp),
                color = LessTransparentWhiteColor,
                fontSize = 14.sp,
                fontFamily = AlataFontFamily(),
                letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
            )


            Spacer(modifier = Modifier.weight(.2f))

            /**
             * Button to add more person to the project
             * **/
            if(role == EnumRoles.Admin || role == EnumRoles.ProAdmin) {
                IconButton(
                    onClick = onClickInvite,
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = "Button to add more person to the project",
                        tint = Color.White
                    )
                }
                /**
                 * Delete the project
                 * **/
                IconButton(
                    onClick = {
                        onDeleteItemClicked()
                    },
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "Button to Delete the project",
                        tint = Color.White
                    )
                }
            }

            /**
             * Silent Notification for this project
             * **/
            IconButton(
                onClick = {
                    onNotificationItemClicked()
                },
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Icon(

                    if (notificationsState){
                        Icons.Default.KeyboardArrowDown
                    }else{
                        Icons.Default.KeyboardArrowUp
                    },
                    contentDescription = "Button to Delete the project",
                    tint = Color.White
                )
            }

        }

    }


}