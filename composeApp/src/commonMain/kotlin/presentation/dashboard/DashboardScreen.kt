package presentation.dashboard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import common.menuItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.dashboard.helpers.DashboardUIState
import presentation.drawer.NavigationDrawer
import presentation.drawer.components.TopBar
import presentation.theme.getLightThemeColor
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor

@Composable
fun DashboardScreen(
    uiState: DashboardUIState = DashboardUIState(),
    logout: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val darkTheme = isSystemInDarkTheme()

    var maximizeCurrentScreen by remember {
        mutableStateOf(true)
    }

    val statusBarColor by animateColorAsState(
        if (maximizeCurrentScreen) {
            getLightThemeColor()
        } else {
            if (darkTheme) {
                getNightDarkColor()
            } else {
                getNightLightColor()
            }
        }, label = ""
    )


    val scale = animateFloatAsState(
        if (maximizeCurrentScreen) {
            1f
        } else {
            0.8f
        }, label = ""
    )

    val roundness = animateDpAsState(
        if (maximizeCurrentScreen) {
            0.dp
        } else {
            40.dp
        }, label = ""
    )

    val offSetX = animateDpAsState(
        if (maximizeCurrentScreen) {
            0.dp
        } else {
            250.dp
        }, label = ""
    )

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
                    menuItems = menuItems,
                    onMenuItemClick = { menuId ->
                        when (menuId.id) {

                        }
                    },
                    closeDrawer = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        maximizeCurrentScreen = true
                    },
                    logout = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        maximizeCurrentScreen = true
                        logout()
                    },
                    modifier = Modifier.width(250.dp),
                    openProfile = {
                        scope.launch {
                            scaffoldState.drawerState.close()
                            maximizeCurrentScreen = true
                            delay(500)
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                        .clickable(
                            onClick = {
                                if (scaffoldState.drawerState.isOpen) {
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                    maximizeCurrentScreen = true
                                }
                            }
                        )
                )
            }
        }
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


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        x = offSetX.value,
                        y = 0.dp
                    )
                    .scale(scale.value)
                    .background(
                        color = getLightThemeColor(),
                        shape = RoundedCornerShape(roundness.value)
                    )
                    .clip(shape = RoundedCornerShape(roundness.value)),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {


                /**
                 * Top app bar
                 * **/
                TopBar(
                    modifier = Modifier.height(60.dp),
                    notificationsState = true,
                    onMenuItemClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                        maximizeCurrentScreen = false
                    },
                    onNotificationsClicked = {
                        //TODO: Add notifications
                    }
                )


               /* NavHost(
                    navController = navController,
                    startDestination = DestinationProjectsRoute,
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                }*/
            }
        }
    }

}