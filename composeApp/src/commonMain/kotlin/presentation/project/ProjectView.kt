package presentation.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import common.EnumProjectColors
import common.EnumRoles
import common.getColor
import common.getRandomColor
import common.maxTitleCharsAllowed
import data.local.mappers.toProject
import data.local.mappers.toUser
import data.local.relations.TaskWithProject
import domain.models.Project
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.onboarding.components.NextButton
import presentation.project.components.DoTooItemsLazyColumn
import presentation.project.helpers.ProjectScreenState
import presentation.shared.ProjectCardWithProfiles
import presentation.shared.editboxs.EditOnFlyBoxRound
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooRed
import presentation.theme.getDarkThemeColor
import presentation.theme.getTextColor

@Composable
fun ProjectView(
    fetchScreenData: () -> Unit = {},
    uiState: ProjectScreenState,
    onToggle: (TaskWithProject) -> Unit = {},
    navigateToCreateTask: () -> Unit = {},
    deleteTask: (TaskWithProject) -> Unit = {},
    deleteProject: () -> Unit = {},
    upsertProject: (Project) -> Unit = {},
    onClickInvite: () -> Unit,
    navigateToEditTask: (task: TaskWithProject) -> Unit,
    navigateToChat: () -> Unit,
    updateTaskTitle: (task: TaskWithProject, title: String) -> Unit
) {


    val showViewerPermissionDialog = remember {
        mutableStateOf(false)
    }
/*

    val showProRequiredDialog = remember {
        mutableStateOf(false)
    }
*/


    var showBlur by remember {
        mutableStateOf(false)
    }


    val taskToDelete = remember {
        mutableStateOf<TaskWithProject?>(null)
    }
    val showDeleteConfirmationDialog = remember {
        mutableStateOf(false)
    }

    val taskToEdit = remember {
        mutableStateOf<TaskWithProject?>(null)
    }

    val focusScope = rememberCoroutineScope()
    val keyBoardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = getDarkThemeColor().copy(alpha = 0.7f)
            )
            .blur(
                radius = if (showBlur) {
                    20.dp
                } else {
                    0.dp
                }
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {

        /**
         *Top Project Card
         * **/
        ProjectCardWithProfiles(
            project = uiState.project.toProject(),
            users = uiState.users.map { it.toUser() },
            onItemDeleteClick = deleteProject,
            updateProjectTitle = { _ ->

            },
            updateProjectDescription = { _ ->

            },
            toggleNotificationSetting = {},
            onClickInvite = onClickInvite,
            showDialogBackgroundBlur = {
                showBlur = it
            }
        )


        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            TextButton(
                onClick = {

                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = "Chat",
                    fontFamily = AlataFontFamily(),
                    color = getTextColor()
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Rounded.Email,
                    contentDescription = "show less or more button",
                    tint = getTextColor()
                )

            }
            if (uiState.role == EnumRoles.Admin) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Lock icon",
                    tint = DoTooRed,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }

            /**
             * List of tasks form this project
             * **/
            DoTooItemsLazyColumn(
                tasks = uiState.tasks.map { task ->
                    TaskWithProject(
                        task = task,
                        projectEntity = uiState.project
                    )
                }.toCollection(ArrayList()),
                onToggleDoToo = { task ->
                    if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                        showBlur = true
                        showViewerPermissionDialog.value = true
                    } else {
                        onToggle(task)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp),
                onItemDelete = { task ->
                    if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                        showBlur = true
                        showViewerPermissionDialog.value = true
                    } else {
                        if (true) {
                            deleteTask(task)
                        } else {
                            taskToDelete.value = task
                            showBlur = true
                            showDeleteConfirmationDialog.value = true
                        }
                    }
                },
                navigateToEditTask = { task ->
                    if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                        showBlur = true
                        showViewerPermissionDialog.value = true
                    } else {
                        navigateToEditTask(task)
                    }
                },
                navigateToQuickEditTask = { task ->
                    if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                        showBlur = true
                        showViewerPermissionDialog.value = true
                    } else {
                        taskToEdit.value = task
                        keyBoardController?.show()
                        showBlur = true
                        focusScope.launch {
                            delay(500)
                            focusRequester.requestFocus()
                        }
                    }
                }
            )

    }

    AnimatedVisibility(
        visible = showBlur && taskToEdit.value != null,
        enter = slideInVertically(
            animationSpec = tween(
                durationMillis = 200,
                easing = EaseIn
            ),
            initialOffsetY = {
                it / 2
            }
        ),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = 200,
                easing = EaseOut
            ),
            targetOffsetY = {
                it / 2
            }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .clickable(
                    onClick = {
                        showBlur = false
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            EditOnFlyBoxRound(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onSubmit = { title ->
                    taskToEdit.value?.let {
                        updateTaskTitle(it, title)
                    }
                    keyBoardController?.hide()
                    showBlur = false
                },
                placeholder = taskToEdit.value?.task?.title ?: "",
                label = "Edit Task",
                maxCharLength = maxTitleCharsAllowed,
                onCancel = {
                    showBlur = false
                },
                themeColor = taskToEdit.value?.projectEntity?.color?.getColor()
                    ?: getRandomColor().getColor(),
                lines = 2
            )

        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        NextButton(
            backgroundColor = EnumProjectColors.Blue.getColor(),
            label = "Add Task",
            onClick = {
                navigateToCreateTask()
            },
        )
    }
    LaunchedEffect(key1 = Unit){
        fetchScreenData()
    }

}
