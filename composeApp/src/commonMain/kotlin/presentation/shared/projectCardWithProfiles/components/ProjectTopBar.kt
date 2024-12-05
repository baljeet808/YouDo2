package presentation.shared.projectCardWithProfiles.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import common.EnumRoles
import common.getRandomAvatar
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
    modifier: Modifier,
    adminId : String,
    adminName : String,
    adminAvatar : String,
    imagesWidthAndHeight: Int = 30,
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

            UserImageAndRoleInfo(
                adminAvatar = adminAvatar,
                adminName = adminName,
                role = role,
                imagesWidthAndHeight = imagesWidthAndHeight
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

@ExperimentalResourceApi
@Composable
fun UserImageAndRoleInfo(
    adminAvatar : String = "",
    adminName : String = "",
    role : EnumRoles = EnumRoles.Viewer,
    imagesWidthAndHeight: Int = 30
){
    Row {
        CoilImage(
            imageModel = { adminAvatar.ifEmpty { getRandomAvatar() } },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 5.dp)
                .width(imagesWidthAndHeight.dp)
                .height(imagesWidthAndHeight.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        )
        Text(
            text = if(role == EnumRoles.Admin || role == EnumRoles.ProAdmin) "You are Admin" else adminName,
            modifier = Modifier
                .padding(start = 10.dp, end = 5.dp),
            color = LessTransparentWhiteColor,
            fontSize = 14.sp,
            fontFamily = AlataFontFamily(),
            letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
        )
    }
}

@ExperimentalResourceApi
@Composable
fun UserImageAndRoleInfoColumn(
    adminAvatar : String = "",
    adminName : String = "",
    imagesWidthAndHeight: Int = 30
){
    Row(  verticalAlignment = Alignment.CenterVertically) {
        CoilImage(
            imageModel = { adminAvatar.ifEmpty { getRandomAvatar() } },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            ),
            modifier = Modifier
                .padding(start = 10.dp, end = 5.dp)
                .width(imagesWidthAndHeight.dp)
                .height(imagesWidthAndHeight.dp)
                .clip(shape = RoundedCornerShape(20.dp))
        )
        Text(
            text = adminName,
            modifier = Modifier
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .padding(start = 5.dp, end = 5.dp)
            ,
            color = LessTransparentWhiteColor,
            fontSize = 12.sp,
            fontFamily = AlataFontFamily(),
            letterSpacing = TextUnit(value = 1f, TextUnitType.Sp),
            textAlign = TextAlign.Center
        )
    }
}