package presentation.dashboard.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import domain.dto_helpers.DataError
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.complete_profile.helpers.DESTINATION_COMPLETE_PROFILE_ROUTE
import presentation.createproject.helpers.DESTINATION_CREATE_PROJECT_ROUTE
import presentation.dashboard.DashboardScreen
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.project.helpers.DESTINATION_PROJECT_ROUTE

const val DESTINATION_DASHBOARD_ROUTE = "dashboard"
const val DESTINATION_DASHBOARD_ROUTE_RELATIVE_PATH = "/{uid}"

@ExperimentalResourceApi
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addDashboardDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
){
    composable(
        route = DESTINATION_DASHBOARD_ROUTE.plus(DESTINATION_DASHBOARD_ROUTE_RELATIVE_PATH),
        arguments = listOf(
            navArgument("uid") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ){ backStackEntry ->

        val userId = backStackEntry.arguments?.getString("uid")

        val viewModel = koinViewModel<DashboardViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                userId?.let {
                    viewModel.fetchData(userId = it)
                }
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to login screen if logged out
        LaunchedEffect(key1 = uiState.isLoggedOut){
            if(uiState.isLoggedOut){
                navController.navigate(DESTINATION_LOGIN_ROUTE){
                    popUpTo(DESTINATION_DASHBOARD_ROUTE){
                        inclusive = true
                    }
                }
            }
        }

        DashboardScreen(
            uiState = uiState,
            logout = {
                viewModel.attemptLogout()
            },
            navigateToCompleteProfile = {
                navController.navigate(DESTINATION_COMPLETE_PROFILE_ROUTE.plus("/${uiState.userId}/${uiState.userEmail}/${false}"))
            },
            navigateToCreateProject = {
                navController.navigate(DESTINATION_CREATE_PROJECT_ROUTE.plus("/${uiState.userId}"))
            },
            loadData = {
                userId?.let {
                    viewModel.fetchData(userId = it)
                }
            },
            navigateToProject = { projectId ->
                navController.navigate(DESTINATION_PROJECT_ROUTE.plus("/${projectId}/${uiState.userId}"))
            }
        )
    }
}