package presentation.projects

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.local.entities.ProjectEntity
import data.local.relations.TaskWithProject
import presentation.projects.components.DialogState
import presentation.projects.components.ProjectsLazyRow
import presentation.projects.helpers.ProjectsUIState
import presentation.shared.AppCustomDialog
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.LightAppBarIconsColor
import presentation.theme.getDarkThemeColor
import presentation.theme.getLightThemeColor
import presentation.theme.getTextColor

@Composable
fun ProjectsView(
    navigateToDoToos: (project: ProjectEntity) -> Unit = {},
    uiState : ProjectsUIState,
    onToggleTask: (TaskWithProject) -> Unit= {},
    navigateToTask: (TaskWithProject) -> Unit= {},
    navigateToCreateTask: () -> Unit= {},
    navigateToCreateProject: () -> Unit= {},
    deleteTask:(TaskWithProject) -> Unit= {},
    updateTaskTitle: (task: TaskWithProject, title: String) -> Unit = {_,_->},
    hideProjectTasksFromDashboard : (project : ProjectEntity) -> Unit= {}
) {

    val dialogState by remember { mutableStateOf<DialogState>(DialogState.None) }

    var showBlur by remember { mutableStateOf(false) }

    showBlur = dialogState != DialogState.None

    when (dialogState) {
        is DialogState.ViewerPermission -> {
            AppCustomDialog(
                onDismiss = {
                    showBlur = false
                },
                onConfirm = {
                    showBlur = false
                },
                title = "Permission Issue! 😣",
                description = "Sorry, you are a viewer in this project. And viewer can not create, edit, update or delete tasks. Ask Project Admin for permission upgrade.",
                topRowIcon = Icons.Default.Lock,
                onChecked = {  },
                showCheckbox = false,
                modifier = Modifier
            )
        }
        is DialogState.DeleteConfirmation -> {
            val taskToDelete = (dialogState as DialogState.DeleteConfirmation).task
            AppCustomDialog(
                onDismiss = {
                    showBlur = false
                },
                onConfirm = {
                    taskToDelete.let(deleteTask)
                    showBlur = false
                },
                title = "Delete this task?",
                description = "Are you sure, you want to permanently delete Following task? \n \"${taskToDelete.task.title}\"",
                topRowIcon = Icons.Default.Delete,
                showDismissButton = true,
                dismissButtonText = "Abort",
                confirmButtonText = "Yes, proceed",
                showCheckbox = false, //SharedPref.deleteTaskWithoutConfirmation.not(),
                onChecked = {
                    //SharedPref.deleteTaskWithoutConfirmation = true
                },
                checkBoxText = "Delete without confirmation next time?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
        else -> {} // No dialog to show
    }

    val projectsListState = rememberLazyListState()

    //SharedPref.showProjectsInitially
    var showTopInfo by remember {
        mutableStateOf(true)
    }

    Scaffold(
        floatingActionButton = {
            androidx.compose.material.FloatingActionButton(
                onClick = navigateToCreateTask,
                modifier = Modifier.height(50.dp),
                backgroundColor = getDarkThemeColor()
            ) {
                Row(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = "Floating button to quickly add a task to this project",
                        tint = getTextColor()
                    )
                    Text(
                        text = "Add Task",
                        fontFamily = AlataFontFamily(),
                        color = getTextColor(),
                        fontSize = 14.sp
                    )
                }
            }
        }
    ) { padding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = getLightThemeColor()
                )
                .blur(
                    radius = if (showBlur) {
                        20.dp
                    } else {
                        0.dp
                    }
                )
                .padding(paddingValues = padding)
        ) {


            /**
             * Main content on screen
             * **/
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {


                AnimatedVisibility(visible = showTopInfo) {
                    Column(modifier = Modifier.fillMaxWidth()) {


                        /**
                         * Greeting text
                         * **/
                        Text(
                            text = "Hello, there!"
                            /*if (SharedPref.userName.length > 8) {
                                "Hi, ${SharedPref.userName}!"
                            } else {
                                "What's up, ${SharedPref.userName}!"
                            }*/,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                            fontFamily = AlataFontFamily(),
                            fontSize = 38.sp,
                            color = getTextColor()
                        )

                        /**
                         * Top Row for greeting and Add project button
                         * **/
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            /**
                             * Simple Projects heading
                             * **/
                            Text(
                                text = "Projects".uppercase(),
                                color = LightAppBarIconsColor,
                                fontFamily = AlataFontFamily(),
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(5.dp),
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )

                            /**
                             * Create Project Button
                             * **/

                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .background(
                                        color = getDarkThemeColor(),
                                        shape = RoundedCornerShape(60.dp)
                                    )
                                    .padding(start = 8.dp, end = 8.dp)
                                    .clickable(onClick = navigateToCreateProject),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Add,
                                    contentDescription = "Floating button to add a project",
                                    tint = getTextColor()
                                )
                                Text(
                                    text = "Add Project",
                                    fontFamily = AlataFontFamily(),
                                    color = getTextColor(),
                                    fontSize = 13.sp
                                )
                            }
                        }

                        /**
                         * Horizontal list of all projects
                         * **/
                        ProjectsLazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            projects = uiState.projects.sortedBy { p -> p.project.updatedAt }.reversed(),
                            navigateToDoToos = navigateToDoToos,
                            listState = projectsListState,
                            hideProjectTasksFromDashboard = { project ->
                                hideProjectTasksFromDashboard(project)
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    /**
                     * Show less/more button
                     * **/
                    TextButton(
                        onClick = { showTopInfo = showTopInfo.not() },
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            text = if (showTopInfo) {
                                "Hide projects"
                            } else {
                                "Show projects"
                            },
                            fontFamily = AlataFontFamily(),
                            color = getTextColor()
                        )
                        Icon(
                            if (showTopInfo) {
                                Icons.Filled.Add
                            } else {
                                Icons.Filled.Add
                            },
                            contentDescription = "show less or more button",
                            tint = getTextColor()
                        )
                    }
                    TextButton(
                        onClick = {

                        },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Icon(
                            if (true) {
                                Icons.AutoMirrored.Outlined.List
                            } else {
                                Icons.AutoMirrored.Outlined.List
                            },
                            contentDescription = "Button to change task list style",
                            tint = getTextColor()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (true) {
                                "Calendar View"
                            } else {
                                "Priorities View"
                            },
                            fontFamily = AlataFontFamily(),
                            color = getTextColor()
                        )

                    }

                }

                /**
                 * Box with tasks view layer and empty view layer
                 * **/
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {


                }

            }
        }

    }
}



