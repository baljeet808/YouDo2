package presentation.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumRoles
import common.getColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import data.local.entities.TaskEntity
import domain.models.Project
import domain.models.User
import presentation.shared.editboxs.EditOnFlyBox
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooYellow
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightTransparentWhiteColor

@Composable
fun ProjectCardWithProfiles(
    project: Project,
    users: List<User> = emptyList(),
    onItemDeleteClick: () -> Unit = {},
    updateProjectTitle: (title: String) -> Unit = {},
    updateProjectDescription: (title: String) -> Unit = {},
    toggleNotificationSetting: () -> Unit = {},
    onClickInvite: () -> Unit = {},
    showFullCardInitially : Boolean = true,
    showDialogBackgroundBlur : (showBlur : Boolean) -> Unit = {},
    role : EnumRoles = EnumRoles.Viewer,
) {

    var showAll by remember {
        mutableStateOf(showFullCardInitially)
    }

    val showViewerPermissionDialog = remember {
        mutableStateOf(false)
    }

    var showEditTitleBox by remember {
        mutableStateOf(false)
    }
    var showEditDescriptionBox by remember {
        mutableStateOf(false)
    }

    if (showViewerPermissionDialog.value){
        AppCustomDialog(
            onDismiss = {
                showDialogBackgroundBlur(false)
                showViewerPermissionDialog.value = false
            },
            onConfirm = {
                showDialogBackgroundBlur(false)
                showViewerPermissionDialog.value = false
            },
            title = "Permission Issue! 😣",
            description = "Sorry, only project owner can edit project details.",
            topRowIcon = Icons.Default.Lock,
            onChecked = {  },
            showCheckbox = false,
            modifier = Modifier
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = project.color.getColor()),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        var themeColor = project.color.getColor()

        Canvas(modifier = Modifier.fillMaxWidth(), onDraw = {
            drawCircle(
                color = NightTransparentWhiteColor,
                radius = 230.dp.toPx(),
                center = Offset(
                    x = 40.dp.toPx(),
                    y = 100.dp.toPx()
                )
            )
            drawCircle(
                color = project.color.getColor(),
                radius = 100.dp.toPx(),
                center = Offset(
                    x = 50.dp.toPx(),
                    y = 100.dp.toPx()
                )
            )

            //creating lines using canvas
            for (i in 1..6) {
                drawLine(
                    color = NightTransparentWhiteColor,
                    strokeWidth = 4.dp.toPx(),
                    start = Offset(
                        x = (170 + (i * 25)).dp.toPx(),
                        y = (0).dp.toPx()
                    ),
                    end = Offset(
                        x = (160).dp.toPx(),
                        y = (10 + (i * 25)).dp.toPx()
                    )
                )
            }
            for (i in 1..8) {
                drawLine(
                    color = NightTransparentWhiteColor,
                    strokeWidth = 4.dp.toPx(),
                    start = Offset(
                        x = (320 + (i * 25)).dp.toPx(),
                        y = (0).dp.toPx()
                    ),
                    end = Offset(
                        x = (135 + (i * 25)).dp.toPx(),
                        y = (185).dp.toPx()
                    )
                )
            }
        })

        AnimatedVisibility(visible = showAll) {
            ProjectTopBar(
                notificationsState = true,
                onNotificationItemClicked = { /*TODO*/ },
                onDeleteItemClicked = onItemDeleteClick,
                onClickInvite = onClickInvite,
                modifier = Modifier,
                role = role
            )
        }

        AnimatedVisibility(visible = showAll) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilesLazyRow(
                    profiles = users,
                    onTapProfiles = {
                        //TODO: show profiles card
                    },
                    visiblePictureCount = 5,
                    imagesWidthAndHeight = 30,
                    spaceBetween = 8,
                    lightColor = DoTooYellow
                )
                AnimatedVisibility(visible = showEditDescriptionBox.not()) {
                    Text(
                        text = project.description.ifBlank {
                            if (role == EnumRoles.ProAdmin || role == EnumRoles.Admin){
                                "Add Description here..."
                            }else{
                                ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    if(role == EnumRoles.ProAdmin || role == EnumRoles.Admin) {
                                        showEditDescriptionBox = true
                                    }else{
                                        showDialogBackgroundBlur(true)
                                        showViewerPermissionDialog.value = true
                                    }
                                }
                            )
                            .padding(start = 5.dp, end = 5.dp),
                        color = LessTransparentWhiteColor,
                        fontSize = 16.sp,
                        fontFamily = AlataFontFamily(),
                        letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
                    )
                }
                AnimatedVisibility(visible = showEditDescriptionBox) {
                    EditOnFlyBox(
                        modifier = Modifier,
                        onSubmit = { desc ->
                           updateProjectDescription(desc)
                            showEditDescriptionBox = false
                        },
                        placeholder = project.description ,
                        label = "Project Description",
                        maxCharLength = maxDescriptionCharsAllowed,
                        onCancel = {
                            showEditDescriptionBox = false
                        },
                        themeColor = project.color.getColor(),
                        lines = 3
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.End
        ) {
            AnimatedVisibility(visible = showEditTitleBox.not()) {
                Text(
                    text = project.name.ifBlank {
                        if(role == EnumRoles.ProAdmin || role == EnumRoles.Admin){
                            "Add title here..."
                        }else{
                            "No title yet 🤪"
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable(
                            onClick = {
                                if(role == EnumRoles.ProAdmin || role == EnumRoles.Admin) {
                                    showEditTitleBox = true
                                }else{
                                    showDialogBackgroundBlur(true)
                                    showViewerPermissionDialog.value = true
                                }
                            }
                        )
                        .fillMaxWidth(),
                    fontFamily = AlataFontFamily(),
                    fontSize = 38.sp,
                    color = Color.White,
                    lineHeight = TextUnit(49f, TextUnitType.Sp)
                )
            }
            AnimatedVisibility(visible = showEditTitleBox) {
                EditOnFlyBox(
                    modifier = Modifier,
                    onSubmit = { title ->
                        updateProjectTitle(title)
                        showEditTitleBox = false
                    },
                    placeholder = project.name ,
                    label = "Project Title",
                    maxCharLength = maxTitleCharsAllowed,
                    onCancel = {
                        showEditTitleBox = false
                    },
                    themeColor = project.color.getColor(),
                    lines = 2
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = emptyList<TaskEntity>().size.toString().plus(" Tasks"),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp),
                    color = LessTransparentWhiteColor,
                    fontSize = 16.sp,
                    fontFamily = AlataFontFamily(),
                    letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
                )

                TextButton(
                    onClick = { showAll = showAll.not() },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        text = if (showAll) {
                            "Show less"
                        } else {
                            "Show more"
                        },
                        fontFamily = AlataFontFamily(),
                        color = Color.White
                    )
                    Icon(
                        if (showAll) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = "show less or more button",
                        tint = Color.White
                    )
                }

            }
        }
    }
}



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