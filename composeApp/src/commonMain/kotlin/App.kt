
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import domain.dto_helpers.DataError
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.chat.addChatViewDestination
import presentation.complete_profile.helpers.addCompleteProfileDestination
import presentation.create_task.addCreateTaskViewDestination
import presentation.createproject.helpers.addCreateProjectViewDestination
import presentation.dashboard.helpers.DESTINATION_DASHBOARD_ROUTE
import presentation.dashboard.helpers.addDashboardDestination
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.login.helpers.addLoginDestination
import presentation.onboarding.helpers.DESTINATION_ONBOARDING_ROUTE
import presentation.onboarding.helpers.addOnboardingDestination
import presentation.project.helpers.addProjectViewDestination
import presentation.projects.helpers.addProjectsViewDestination
import presentation.shared.BackgroundCircles
import presentation.shared.dialogs.AlertDialogView
import presentation.shared.dialogs.LoadingDialog
import presentation.signup.helpers.addSignupDestination
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor


@Composable
@Preview
@ExperimentalResourceApi
fun App(
) {

    val viewModel = koinViewModel<AppViewModel>()

    val userState = viewModel.userState

    val retryApiCall = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }

    val errorType = remember { mutableStateOf<DataError?>(null) }

    val navController = rememberNavController()

    val dialogTitle: String
    val dialogText: String
    var dismissButtonText = "Dismiss"
    val confirmButtonText: String
    val showConfirmButton = true
    val showDismissButton = true
    val confirmAction: () -> Unit
    var dismissAction: () -> Unit = {
        retryApiCall.value = false
        openAlertDialog.value = false
    }
    val icon: ImageVector

    val navAnimationDuration = 300 //millis



    val startDestination = if (userState.hasOnboarded) {
        DESTINATION_ONBOARDING_ROUTE
    } else {
        if (userState.isUserLoggedIn) {
            DESTINATION_DASHBOARD_ROUTE.plus("/${userState.userId}")
        } else {
            DESTINATION_LOGIN_ROUTE
        }
    }

    MaterialTheme {

        Box (
            modifier = Modifier.fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme()) getNightDarkColor() else getNightLightColor()
                )
        ) {
            BackgroundCircles(navController = navController)

            AnimatedVisibility(visible = userState.loading, enter = fadeIn(),exit = fadeOut()) {
                LoadingDialog()
            }
            AnimatedVisibility(visible = userState.loading.not(), enter = fadeIn()) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(navAnimationDuration)
                        ) +
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(navAnimationDuration)
                                )
                    },
                    exitTransition = {
                        fadeOut(
                            animationSpec = tween(navAnimationDuration)
                        ) + slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(navAnimationDuration)
                        )
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(navAnimationDuration)) + slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(navAnimationDuration)
                        )
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(navAnimationDuration)) + slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(navAnimationDuration)
                        )
                    }
                ){
                    addLoginDestination(
                        navController = navController,
                        showErrorAlertDialog = { error ->
                            retryApiCall.value = false
                            if (error != null) {
                                errorType.value = error
                                openAlertDialog.value = true
                            } else {
                                openAlertDialog.value = false
                            }
                        },
                        retryApiCall = retryApiCall
                    )
                    addOnboardingDestination(navController = navController)
                    addSignupDestination(
                        navController = navController,
                        showErrorAlertDialog = { error ->
                            retryApiCall.value = false
                            if (error != null) {
                                errorType.value = error
                                openAlertDialog.value = true
                            } else {
                                openAlertDialog.value = false
                            }
                        },
                        retryApiCall = retryApiCall
                    )
                    addDashboardDestination(
                        navController = navController,
                        showErrorAlertDialog = { error ->
                            retryApiCall.value = false
                            if (error != null) {
                                errorType.value = error
                                openAlertDialog.value = true
                            } else {
                                openAlertDialog.value = false
                            }
                        },
                        retryApiCall = retryApiCall
                    )
                    addCompleteProfileDestination(
                        navController = navController,
                        showErrorAlertDialog = { error ->
                            retryApiCall.value = false
                            if (error != null) {
                                errorType.value = error
                                openAlertDialog.value = true
                            } else {
                                openAlertDialog.value = false
                            }
                        },
                        retryApiCall = retryApiCall
                    )
                    addProjectsViewDestination(navController = navController)
                    addCreateProjectViewDestination(navController = navController)
                    addCreateTaskViewDestination(navController = navController)
                    addProjectViewDestination(
                        navController = navController,
                        showErrorAlertDialog = { error ->
                            retryApiCall.value = false
                            if (error != null) {
                                errorType.value = error
                                openAlertDialog.value = true
                            } else {
                                openAlertDialog.value = false
                            }
                        },
                        retryApiCall = retryApiCall
                    )
                    addChatViewDestination(navController = navController)
                }
            }
        }
    }
    when (errorType.value) {
        DataError.Network.NO_INTERNET -> {
            dialogTitle = "No Internet Connection."
            dialogText = "Please check your internet connection and try again!"
            icon = Icons.Default.Info
            confirmButtonText = "Retry"
            confirmAction = {
                retryApiCall.value = true
                openAlertDialog.value = false
            }
        }
        DataError.Network.ALL_OTHER -> {
            dialogTitle = "Oops! We hit a wall."
            dialogText = "Something went wrong with the network request. Please try again!"
            confirmButtonText = "Retry"
            icon = Icons.Default.Info
            confirmAction = {
                retryApiCall.value = true
                openAlertDialog.value = false
            }
        }
        DataError.Network.NOT_FOUND -> {
            dialogTitle = "No resource found."
            dialogText = "We looked every where and couldn't find what you were looking for."
            icon = Icons.Default.Info
            confirmButtonText = "Try again!"
            confirmAction = {
                retryApiCall.value = true
                openAlertDialog.value = false
            }
            dismissButtonText = if(navController.currentDestination?.route != DESTINATION_LOGIN_ROUTE ){ "Go Back"} else { "Dismiss" }
            dismissAction = {
                retryApiCall.value = false
                openAlertDialog.value = false
                if(navController.currentDestination?.route != DESTINATION_LOGIN_ROUTE){
                    navController.popBackStack()
                }
            }
        }
        else -> {
            dialogTitle = "Oops! Something Went wrong. ".plus(errorType.toString())
            dialogText = "Report this bug to us, we will look into it, ASAP!"
            icon = Icons.Default.Info
            confirmButtonText = "Retry"
            confirmAction = {
                retryApiCall.value = true
                openAlertDialog.value = false
            }
        }
    }

    when {
        openAlertDialog.value -> {
            AlertDialogView(
                dialogTitle = dialogTitle,
                dialogText = dialogText,
                icon = icon,
                dismissButtonText = dismissButtonText,
                confirmButtonText = confirmButtonText,
                showConfirmButton = showConfirmButton,
                showDismissButton = showDismissButton,
                onDismissRequest = dismissAction,
                onConfirmation = confirmAction
            )
        }
    }

}