package presentation.createproject.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.createproject.CreateProjectView

const val DESTINATION_CREATE_PROJECT_ROUTE = "create_project"
const val DESTINATION_CREATE_PROJECT_ROUTE_RELATIVE_PATH = "/{userId}"

@ExperimentalResourceApi
fun NavGraphBuilder.addCreateProjectViewDestination(
    navController: NavController
){
    composable(
        route = DESTINATION_CREATE_PROJECT_ROUTE.plus(DESTINATION_CREATE_PROJECT_ROUTE_RELATIVE_PATH),
        arguments = listOf(
            navArgument("userId") {
                type = NavType.StringType
            }
        )
    ){ backStackEntry ->
        val userId = backStackEntry.arguments?.getString("userId")

        val viewModel = koinViewModel<CreateProjectViewModel>()
        val uiState = viewModel.uiState

        //take user to dashboard if project created
        LaunchedEffect(key1 = uiState.success) {
            if(uiState.success){
                navController.popBackStack()
            }
        }

        CreateProjectView(
            uiState = uiState,
            onScreenEvent = {
                viewModel.onScreenEvent(it)
            },
            navigateBack = {
                navController.popBackStack()
            },
            setUpData = {
                userId?.let {
                    viewModel.getUserDetails(it)
                }
            }
        )
    }
}