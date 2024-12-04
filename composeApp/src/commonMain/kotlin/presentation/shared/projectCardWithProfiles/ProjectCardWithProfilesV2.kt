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
import androidx.compose.runtime.LaunchedEffect
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
import domain.models.Project
import domain.models.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import presentation.shared.ProfilesLazyRow
import presentation.shared.dialogs.AlertDialogView
import presentation.shared.dialogs.AppCustomDialog
import presentation.shared.editboxs.EditOnFlyBox
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.projectCardWithProfiles.helpers.ProjectCardWithProfilesEvent
import presentation.shared.projectCardWithProfiles.helpers.ProjectCardWithProfilesViewModel
import presentation.theme.DoTooYellow
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightTransparentWhiteColor

@ExperimentalResourceApi
@Composable
fun ProjectCardWithProfilesV2(
    project: Project,
    onClickInvite: () -> Unit = {},
    showProjectDetailInitially : Boolean = true,
    openProject : () -> Unit = {},
    currentUser : User
) {

    val viewModel = koinViewModel<ProjectCardWithProfilesViewModel>()
    val uiState = viewModel.uiState


    AnimatedVisibility(
        visible = uiState.showViewerPermissionDialog,
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
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnTogglePermissionDialogVisibility)
            },
            onConfirm = {
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnTogglePermissionDialogVisibility)
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
        visible = uiState.showConfirmProjectDeletionDialog,
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
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleProjectDeletionDialogVisibility)
            },
            onConfirmation = {
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnDeleteProject)
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleProjectDeletionDialogVisibility)
            },
            dialogTitle = "Are you sure?",
            dialogText = "This will permanently delete the project.",
            icon = Icons.Default.Delete,
            showDismissButton = true,
            showConfirmButton = true,
        )
    }

    AnimatedVisibility(
        visible = uiState.showConfirmExitProjectDialog,
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
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleExitProjectDialogVisibility)
            },
            onConfirmation = {
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnExitProject)
                viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleExitProjectDialogVisibility)
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

        AnimatedVisibility(visible = uiState.showProjectDetail) {

        }

        AnimatedVisibility(visible = uiState.showProjectDetail) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilesLazyRow(
                    profiles = uiState.usersInProject,
                    onTapProfiles = {
                        //TODO: show profiles card
                    },
                    visiblePictureCount = 5,
                    imagesWidthAndHeight = 30,
                    spaceBetween = 8,
                    lightColor = DoTooYellow
                )
                AnimatedVisibility(visible = uiState.showEditDescriptionBox) {
                    Text(
                        text = project.description.ifBlank {
                            if (uiState.userRoleInProject == EnumRoles.ProAdmin || uiState.userRoleInProject == EnumRoles.Admin){
                                "Add Description here..."
                            }else{
                                ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(ProjectCardWithProfilesEvent.OnRequestToOpenProjectDescriptionEditBox)
                                }
                            )
                            .padding(start = 5.dp, end = 5.dp),
                        color = LessTransparentWhiteColor,
                        fontSize = 16.sp,
                        fontFamily = AlataFontFamily(),
                        letterSpacing = TextUnit(value = 2f, TextUnitType.Sp)
                    )
                }
                AnimatedVisibility(visible = uiState.showEditDescriptionBox) {
                    EditOnFlyBox(
                        modifier = Modifier,
                        onSubmit = { desc ->
                            viewModel.onEvent(ProjectCardWithProfilesEvent.OnSaveProjectDescriptionChange(description = desc))
                            viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleDescriptionEditBoxVisibility)
                        },
                        placeholder = project.description ,
                        label = "Project Description",
                        maxCharLength = maxDescriptionCharsAllowed,
                        onCancel = {
                            viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleDescriptionEditBoxVisibility)
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
            AnimatedVisibility(visible = uiState.showEditTitleBox) {
                Text(
                    text = project.name.ifBlank {
                        if(uiState.userRoleInProject == EnumRoles.ProAdmin || uiState.userRoleInProject == EnumRoles.Admin){
                            "Add title here..."
                        }else{
                            "No title yet 🤪"
                        }
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable(
                            onClick = {
                                viewModel.onEvent(ProjectCardWithProfilesEvent.OnRequestToOpenProjectNameEditBox)
                            }
                        )
                        .fillMaxWidth(),
                    fontFamily = AlataFontFamily(),
                    fontSize = 38.sp,
                    color = Color.White,
                    lineHeight = TextUnit(49f, TextUnitType.Sp)
                )
            }
            AnimatedVisibility(visible = uiState.showEditTitleBox) {
                EditOnFlyBox(
                    modifier = Modifier,
                    onSubmit = { title ->
                        viewModel.onEvent(ProjectCardWithProfilesEvent.OnSaveProjectNameChange(name = title))
                        viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleTitleEditBoxVisibility)
                    },
                    placeholder = project.name ,
                    label = "Project Title",
                    maxCharLength = maxTitleCharsAllowed,
                    onCancel = {
                        viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleTitleEditBoxVisibility)
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
                    onClick = {
                        viewModel.onEvent(ProjectCardWithProfilesEvent.OnToggleDetailVisibility)
                    },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(
                        text = if (uiState.showProjectDetail) {
                            "Show less"
                        } else {
                            "Show more"
                        },
                        fontFamily = AlataFontFamily(),
                        color = Color.White
                    )
                    Icon(
                        if (uiState.showProjectDetail) {
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

    LaunchedEffect(key1 = Unit){
        viewModel.onEvent(
            ProjectCardWithProfilesEvent.OnFetchUIData(
                project = project,
                showProjectDetailInitially = showProjectDetailInitially,
                currentUser = currentUser
            )
        )
    }

}
