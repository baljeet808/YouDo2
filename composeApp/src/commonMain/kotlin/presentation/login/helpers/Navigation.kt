package presentation.login.helpers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.login.LoginScreen

const val DESTINATION_LOGIN_ROUTE = "login"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addLoginDestination(
    navController: NavController,
){
    composable(
        route = DESTINATION_LOGIN_ROUTE
    ){

        val viewModel = koinViewModel<LoginViewModel>()
        val uiState = viewModel.uiState

        LoginScreen(
            uiState = uiState,
            login = { email, password ->

            },
            navigateToSignup = {

            },
            navigateToPolicy = {

            }
        )
    }
}