package presentation.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.rememberDismissState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.EnumRoles
import common.getColor
import common.getRole
import common.maxTitleCharsAllowed
import data.local.mappers.toProjectEntity
import domain.models.Task
import domain.models.TaskWithProject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.onboarding.components.NextButton
import presentation.project.components.TaskView
import presentation.project.helpers.ProjectScreenEvent
import presentation.project.helpers.ProjectScreenState
import presentation.shared.editboxs.EditOnFlyBoxRound
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.projectCardWithProfiles.ProjectCardWithProfiles
import presentation.theme.LightAppBarIconsColor

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@ExperimentalResourceApi
@Composable
fun ProjectView(
    fetchScreenData: () -> Unit = {},
    uiState: ProjectScreenState,
    navigateToCreateTask: () -> Unit = {},
    onClickInvite: () -> Unit,
    navigateToEditTask: (task: Task) -> Unit,
    navigateToChat: () -> Unit,
    onEvent: (ProjectScreenEvent) -> Unit = {}
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
        mutableStateOf<Task?>(null)
    }
    val showDeleteConfirmationDialog = remember {
        mutableStateOf(false)
    }

    val taskToEdit = remember {
        mutableStateOf<Task?>(null)
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
                color = Color.Black
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
             * List of tasks form this project
             * **/
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp)
                .fillMaxSize()
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                /**
                 *Top Project Card
                 * **/
                ProjectCardWithProfiles(
                    project = uiState.project,
                    users = uiState.users,
                    onDeleteProjectClick = {
                        onEvent(ProjectScreenEvent.DeleteProject(uiState.project))
                    },
                    onExitProjectClick = {
                        onEvent(ProjectScreenEvent.ExitProject(uiState.project))
                    },
                    updateProject = { project ->
                        onEvent(ProjectScreenEvent.UpdateProject(project))
                    },
                    onClickInvite = onClickInvite,
                    showDialogBackgroundBlur = {
                        showBlur = it
                    },
                    role = getRole(project = uiState.project.toProjectEntity(), userId = uiState.userId),
                    showProjectDetail = uiState.showProjectDetail,
                    onDetailClicked ={
                        onEvent(ProjectScreenEvent.ToggleProjectDetail)
                    }
                )
            }
            items(uiState.tasks, key = {it.id}) { task ->

                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                                showBlur = true
                                showViewerPermissionDialog.value = true
                            } else {
                                if (false) {
                                    onEvent(ProjectScreenEvent.DeleteTask(task))
                                } else {
                                    taskToDelete.value = task
                                    showBlur = true
                                    showDeleteConfirmationDialog.value = true
                                }
                            }
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = state,
                    background = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(start = 20.dp, end = 20.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                androidx.compose.material.Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Deleted task icon",
                                    tint = LightAppBarIconsColor,
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp)
                                )
                                androidx.compose.material.Text(
                                    text = "Delete",
                                    color = LightAppBarIconsColor,
                                    fontSize = 16.sp,
                                    fontFamily = AlataFontFamily()
                                )
                            }
                        }
                    },
                    dismissContent = {
                        TaskView(
                            taskWithProject = TaskWithProject(task = task, project = uiState.project),
                            onToggleDone = {
                                if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                                    showBlur = true
                                    showViewerPermissionDialog.value = true
                                } else {
                                    onEvent(ProjectScreenEvent.ToggleTask(task))
                                }
                            },
                            navigateToQuickEditTask = {
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
                            },
                            navigateToTaskEdit = {
                                if (uiState.role == EnumRoles.Viewer || uiState.role == EnumRoles.Blocked) {
                                    showBlur = true
                                    showViewerPermissionDialog.value = true
                                } else {
                                    navigateToEditTask(task)
                                }
                            }
                        )
                    },
                    directions = setOf(
                        DismissDirection.EndToStart
                    )
                )

            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
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
                        onEvent(ProjectScreenEvent.UpdateTask(it.copy(title = title)))
                    }
                    keyBoardController?.hide()
                    showBlur = false
                },
                placeholder = taskToEdit.value?.title ?: "",
                label = "Edit Task",
                maxCharLength = maxTitleCharsAllowed,
                onCancel = {
                    showBlur = false
                },
                themeColor = uiState.project.color.getColor(),
                lines = 2
            )

        }
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        NextButton(
            backgroundColor = uiState.project.color.getColor(),
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
