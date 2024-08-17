package presentation.signup.helpers

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
import presentation.signup.SignupScreen

const val DESTINATION_SIGNUP_ROUTE = "signup"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addSignupDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
){
    composable(
        route = DESTINATION_SIGNUP_ROUTE
    ){
        val viewModel = koinViewModel<SignupViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.attemptSignup()
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to complete profile screen if signup is successful
        LaunchedEffect(key1 = uiState.signupSuccessful){
            if(uiState.signupSuccessful){
                navController.navigate(DESTINATION_COMPLETE_PROFILE_ROUTE) {
                    popUpTo(DESTINATION_SIGNUP_ROUTE){
                        inclusive = true
                    }
                }
            }
        }

        SignupScreen(
            uiState = uiState,
            signUp = {
                viewModel.attemptSignup()
            },
            onEmailChanged = {
                viewModel.updateEmail(it)
            },
            onPasswordChanged = {
                viewModel.updatePassword(it)
            },
            navigateBackToLogin = {
                navController.popBackStack()
            }
        )

    }
}