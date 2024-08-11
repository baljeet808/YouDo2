import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import common.isUserLoggedInKey
import domain.dto_helpers.DataError
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.dashboard.helpers.DESTINATION_DASHBOARD_ROUTE
import presentation.dashboard.helpers.addDashboardDestination
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.login.helpers.addLoginDestination
import presentation.shared.AlertDialogView
import presentation.signup.helpers.addSignupDestination


@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    val retryApiCall = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }

    val errorType = remember { mutableStateOf<DataError.Network?>(null) }

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

    val navAnimationDuration = 500 //millis
    val userLoggedIn = prefs.data.map { it[isUserLoggedInKey]?:false }.collectAsState(initial = false)

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = if (userLoggedIn.value) DESTINATION_DASHBOARD_ROUTE else DESTINATION_LOGIN_ROUTE,
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
                retryApiCall = retryApiCall,
                prefs = prefs
            )
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
                retryApiCall = retryApiCall,
                prefs = prefs
            )
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