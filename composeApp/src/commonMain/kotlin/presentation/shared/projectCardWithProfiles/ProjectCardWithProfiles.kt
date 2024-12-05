package presentation.shared.projectCardWithProfiles

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
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
import common.PROJECT_USERS_PROFILE_IMAGE_HEIGHT_AND_WIDTH
import common.getColor
import common.maxDescriptionCharsAllowed
import common.maxTitleCharsAllowed
import domain.models.Project
import domain.models.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.ProfilesLazyRow
import presentation.shared.dialogs.AlertDialogView
import presentation.shared.dialogs.AppCustomDialog
import presentation.shared.editboxs.EditOnFlyBox
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.projectCardWithProfiles.components.ProjectTopBar
import presentation.theme.DoTooYellow
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightTransparentWhiteColor

@ExperimentalResourceApi
@Composable
fun ProjectCardWithProfiles(
    project: Project,
    users: List<User> = emptyList(),
    onItemDeleteClick: () -> Unit = {},
    updateProject: (project : Project) -> Unit = {},
    onClickInvite: () -> Unit = {},
    showDialogBackgroundBlur : (showBlur : Boolean) -> Unit = {},
    role : EnumRoles = EnumRoles.Viewer,
    showProjectDetailInitially : Boolean = false,
) {

    val showConfirmExitProjectDialog = remember {
        mutableStateOf(false)
    }

    val showViewerPermissionDialog = remember {
        mutableStateOf(false)
    }

    val showConfirmProjectDeletionDialog = remember {
        mutableStateOf(false)
    }

    var showProjectDetail by remember {
        mutableStateOf(showProjectDetailInitially)
    }

    var showEditTitleBox by remember {
        mutableStateOf(false)
    }
    var showEditDescriptionBox by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        visible = showViewerPermissionDialog.value,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        AppCustomDialog(
            onDismiss = {
                showViewerPermissionDialog.value = false
            },
            onConfirm = {
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

    AnimatedVisibility(
        visible = showConfirmProjectDeletionDialog.value,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        AlertDialogView(
            onDismissRequest = {
                showConfirmProjectDeletionDialog.value = false
            },
            onConfirmation = {
                //viewModel.onEvent(ProjectCardWithProfilesEvent.OnDeleteProject)
                showConfirmProjectDeletionDialog.value = false
            },
            dialogTitle = "Are you sure?",
            dialogText = "This will permanently delete the project.",
            icon = Icons.Default.Delete,
            showDismissButton = true,
            showConfirmButton = true,
        )
    }

    AnimatedVisibility(
        visible = showConfirmExitProjectDialog.value,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        AlertDialogView(
            onDismissRequest = {
                showConfirmExitProjectDialog.value = false
            },
            onConfirmation = {
                //viewModel.onEvent(ProjectCardWithProfilesEvent.OnExitProject)
                showConfirmExitProjectDialog.value = false
            },
            dialogTitle = "Are you sure?",
            dialogText = "Project will be no longer accessible and visible to you.",
            icon = Icons.Default.Warning,
            showDismissButton = true,
            showConfirmButton = true,
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

        AnimatedVisibility(visible = showProjectDetail) {
            ProjectTopBar(
                notificationsState = true,
                onNotificationItemClicked = { /*TODO*/ },
                onDeleteItemClicked = onItemDeleteClick,
                onClickInvite = onClickInvite,
                modifier = Modifier,
                role = role,
                adminId = project.ownerId,
                adminName =  project.ownerName,
                adminAvatar =  project.ownerAvatarUrl,
                imagesWidthAndHeight = PROJECT_USERS_PROFILE_IMAGE_HEIGHT_AND_WIDTH
            )
        }

        AnimatedVisibility(visible = showProjectDetail) {
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
                            val projectCopy = project.copy(description = desc)
                            updateProject(projectCopy)
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
                    fontSize = if(showProjectDetail) 28.sp else 20.sp,
                    color = Color.White,
                    lineHeight = TextUnit(49f, TextUnitType.Sp)
                )
            }
            AnimatedVisibility(visible = showEditTitleBox) {
                EditOnFlyBox(
                    modifier = Modifier,
                    onSubmit = { title ->
                        val projectCopy = project.copy(name = title)
                        updateProject(projectCopy)
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
                    text = project.numberOfTasks.toString().plus(" Tasks"),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp),
                    color = LessTransparentWhiteColor,
                    fontSize = 16.sp,
                    fontFamily = AlataFontFamily(),
                    letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
                )

                TextButton(
                    onClick = { showProjectDetail = showProjectDetail.not() },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        text = if (showProjectDetail) {
                            "Show less"
                        } else {
                            "Show more"
                        },
                        fontFamily = AlataFontFamily(),
                        color = Color.White
                    )
                    Icon(
                        if (showProjectDetail) {
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

