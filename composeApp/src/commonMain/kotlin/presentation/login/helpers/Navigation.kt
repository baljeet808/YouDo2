package presentation.login.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import domain.dto_helpers.DataError
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.dashboard.helpers.DESTINATION_DASHBOARD_ROUTE
import presentation.login.LoginScreen
import presentation.signup.helpers.DESTINATION_SIGNUP_ROUTE

const val DESTINATION_LOGIN_ROUTE = "login"

@ExperimentalResourceApi
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addLoginDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
){
    composable(
        route = DESTINATION_LOGIN_ROUTE
    ){
        val viewModel = koinViewModel<LoginViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.attemptLogin()
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to dashboard if login is successful
        LaunchedEffect(key1 = uiState.loginSuccessful) {
            if(uiState.loginSuccessful){
                navController.navigate(DESTINATION_DASHBOARD_ROUTE.plus("/${uiState.userId}")){
                    popUpTo(DESTINATION_LOGIN_ROUTE){
                        inclusive = true
                    }
                }
            }
        }

        LoginScreen(
            uiState = uiState,
            login = {
                viewModel.attemptLogin()
            },
            navigateToSignup = {
                navController.navigate(DESTINATION_SIGNUP_ROUTE)
            },
            navigateToPolicy = {
                //TODO: navigate to policy
            },
            onPasswordChanged = { password ->
                viewModel.updatePassword(password)
            },
            onEmailChanged = {  email ->
                viewModel.updateEmail(email)
            },

        )
    }
}