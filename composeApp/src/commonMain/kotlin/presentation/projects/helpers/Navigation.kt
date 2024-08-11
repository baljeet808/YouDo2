package presentation.projects.helpers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.projects.ProjectsView


const val DESTINATION_PROJECTS_ROUTE = "projects"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addProjectsViewDestination(
    navController: NavController
){
    composable(
        route = DESTINATION_PROJECTS_ROUTE
    ){

        val viewModel = koinViewModel<ProjectsViewModel>()
        val uiState = viewModel.uiState

        ProjectsView(
            navigateToDoToos = {

            },
            projects = uiState.projects,
            onToggleTask = {

            }
        )

    }
}



