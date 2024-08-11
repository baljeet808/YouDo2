package presentation.login.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import common.isUserLoggedInKey
import domain.dto_helpers.DataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.dashboard.helpers.DESTINATION_DASHBOARD_ROUTE
import presentation.login.LoginScreen
import presentation.signup.helpers.DESTINATION_SIGNUP_ROUTE

const val DESTINATION_LOGIN_ROUTE = "login"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addLoginDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false),
    prefs : DataStore<Preferences>
){
    composable(
        route = DESTINATION_LOGIN_ROUTE
    ){
        val viewModel = koinViewModel<LoginViewModel>()
        val uiState = viewModel.uiState

        val scope = rememberCoroutineScope()

        var emailBackup by remember { mutableStateOf("") }
        var passwordBackup by remember { mutableStateOf("") }

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.attemptLogin(email = emailBackup,password = passwordBackup)
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
                scope.launch(Dispatchers.IO) {
                    prefs.edit { dataStore ->
                        dataStore[isUserLoggedInKey] = true
                    }
                }
                navController.navigate(DESTINATION_DASHBOARD_ROUTE){
                    popUpTo(DESTINATION_LOGIN_ROUTE){
                        inclusive = true
                    }
                }
            }
        }

        LoginScreen(
            uiState = uiState,
            login = { email, password ->
                emailBackup = email
                passwordBackup  = password
                viewModel.attemptLogin(email = email,password = password)
            },
            navigateToSignup = {
                navController.navigate(DESTINATION_SIGNUP_ROUTE)
            },
            navigateToPolicy = {
                //TODO: navigate to policy
            },
            onCredentialsUpdated = { email, password ->
                viewModel.updateCredentials(email, password)
            }
        )
    }
}