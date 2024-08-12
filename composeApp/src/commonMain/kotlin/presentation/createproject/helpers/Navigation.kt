package presentation.createproject.helpers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.createproject.CreateProjectView


const val DESTINATION_CREATE_PROJECT_ROUTE = "create_project"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addCreateProjectViewDestination(
    navController: NavController
){
    composable(
        route = DESTINATION_CREATE_PROJECT_ROUTE
    ){
        val viewModel = koinViewModel<CreateProjectViewModel>()
        val uiState = viewModel.uiState

        CreateProjectView(
            uiState = uiState,
            onScreenEvent = {
                viewModel.onScreenEvent(it)
            },
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}