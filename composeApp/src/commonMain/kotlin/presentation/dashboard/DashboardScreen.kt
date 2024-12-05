package presentation.dashboard

import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.EnumProjectColors
import common.getColor
import common.menuItems
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.complete_profile.helpers.DESTINATION_COMPLETE_PROFILE_ROUTE
import presentation.dashboard.components.SearchBoxView
import presentation.dashboard.helpers.DashboardScreenState
import presentation.dashboard.helpers.DashboardUIState
import presentation.dashboard.helpers.dashboardScreenStateConverter
import presentation.drawer.NavigationDrawer
import presentation.drawer.components.TopBar
import presentation.onboarding.components.NextButton
import presentation.shared.projectCardWithProfiles.ProjectCardForDashboard
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor

@ExperimentalResourceApi
@Composable
fun DashboardScreen(
    uiState: DashboardUIState = DashboardUIState(),
    logout: () -> Unit = {},
    loadData: () -> Unit = {},
    navigateToCompleteProfile: () -> Unit = {},
    navigateToCreateProject: () -> Unit = {},
    navigateToProject: (projectId: String) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)

    val interactionSource = remember { MutableInteractionSource() }
    var offsetX by remember { mutableStateOf(0f) }
    val drawerWidth = 250.dp

    var screenState by remember { mutableStateOf(DashboardScreenState()) }

    if (screenState.isDrawerOpen) {
        scope.launch { scaffoldState.drawerState.open() }
    } else {
        scope.launch { scaffoldState.drawerState.close() }
    }

    val darkTheme = isSystemInDarkTheme()

    val animatedState by animateValueAsState(
        targetValue = if (screenState.isDrawerOpen) {
            DashboardScreenState(
                isDrawerOpen = true,
                statusBarColor = if (darkTheme) getNightDarkColor() else getNightLightColor(), // Or from your theme
                scale = 0.8f,
                roundness = 40.dp,
                offsetX = 250.dp
            )
        } else {
            DashboardScreenState()
        },
        typeConverter = dashboardScreenStateConverter
    )

    fun openDrawer() {
        screenState = screenState.copy(isDrawerOpen = true)
    }

    fun closeDrawer() {
        screenState = screenState.copy(isDrawerOpen = false)
    }


    Scaffold(
        scaffoldState = scaffoldState,
        drawerScrimColor = Color.Transparent,
        drawerGesturesEnabled = false,
        drawerBackgroundColor = Color.Transparent,
        drawerElevation = 0.dp,
        drawerContent = {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationDrawer(
                    userEmail = uiState.userEmail,
                    userName = uiState.userName,
                    avatarUrl = uiState.userAvatarUrl,
                    menuItems = menuItems,
                    onMenuItemClick = { item ->
                        when (item.id) {
                            DESTINATION_COMPLETE_PROFILE_ROUTE -> {
                                navigateToCompleteProfile()
                            }

                            else -> {
                                closeDrawer()
                            }
                        }
                    },
                    closeDrawer = {
                        closeDrawer()
                    },
                    logout = {
                        closeDrawer()
                        logout()
                    },
                    modifier = Modifier.fillMaxWidth(0.85f),
                    openProfile = {
                        closeDrawer()
                    }
                )
            }
        },
        modifier = Modifier
            .draggable(
                interactionSource = interactionSource,
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                },
                onDragStopped = { velocity ->
                    scope.launch {
                        if (velocity > 0 || offsetX > drawerWidth.value / 2) {
                            drawerState.open()
                            screenState = screenState.copy(isDrawerOpen = true)
                            offsetX = drawerWidth.value
                        } else {
                            drawerState.close()
                            screenState = screenState.copy(isDrawerOpen = false)
                            offsetX = 0f
                        }
                    }
                }
            )
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (darkTheme) {
                        getNightDarkColor()
                    } else {
                        getNightLightColor()
                    }
                )
                .padding(padding)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        x = animatedState.offsetX,
                        y = 0.dp
                    )
                    .scale(animatedState.scale)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(animatedState.roundness)
                    )
                    .clip(shape = RoundedCornerShape(animatedState.roundness)),
                contentAlignment = Alignment.Center,
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TopBar(
                        modifier = Modifier.height(70.dp),
                        onMenuItemClick = {
                            openDrawer()
                        },
                        avatarUrl = uiState.userAvatarUrl,
                        userName = uiState.userName
                    )
                    SearchBoxView()
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        LazyColumn (
                            modifier = Modifier.fillMaxWidth().padding(2.dp ),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            item {
                                Spacer(modifier = Modifier.width(5.dp))
                            }
                            items(items = uiState.projects, key = { it.id }) { project ->

                                ProjectCardForDashboard(
                                    project = project,
                                    users = uiState.allProjectUsers.filter { user -> (project.viewerIds + project.collaboratorIds).contains(user.id) },
                                    openProject = {
                                        navigateToProject(project.id)
                                    }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    NextButton(
                        backgroundColor = EnumProjectColors.Blue.getColor(),
                        label = "Add Project",
                        onClick = {
                            navigateToCreateProject()
                        },
                    )
                }
            }

        }
    }

    LaunchedEffect(key1 = Unit) {
        loadData()
    }
}