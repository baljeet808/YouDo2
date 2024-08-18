package presentation.dashboard.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import domain.dto_helpers.DataError
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.complete_profile.helpers.DESTINATION_COMPLETE_PROFILE_ROUTE
import presentation.dashboard.DashboardScreen
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE

const val DESTINATION_DASHBOARD_ROUTE = "dashboard"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addDashboardDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
){
    composable(
        route = DESTINATION_DASHBOARD_ROUTE
    ){
        val viewModel = koinViewModel<DashboardViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.getCurrentUser()
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
            }
        )
    }
}