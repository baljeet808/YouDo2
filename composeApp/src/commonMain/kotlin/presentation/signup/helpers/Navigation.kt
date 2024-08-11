package presentation.signup.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import domain.dto_helpers.DataError
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
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

        var emailBackup by remember { mutableStateOf("") }
        var passwordBackup by remember { mutableStateOf("") }

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.attemptSignup(email = emailBackup, password =  passwordBackup)
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to login screen if signup is successful
        LaunchedEffect(key1 = uiState.signupSuccessful){
            if(uiState.signupSuccessful){
                navController.popBackStack()
            }
        }

        SignupScreen(
            uiState = uiState,
            signUp = { email, password ->
                emailBackup = email
                passwordBackup  = password
                viewModel.attemptSignup(email = email,password = password)
            },
            onCredentialsUpdated = { email, password ->
                viewModel.updateCredentials(email = email, password = password)
            }
        )

    }
}