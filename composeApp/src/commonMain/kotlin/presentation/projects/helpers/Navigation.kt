package presentation.projects.helpers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.createproject.helpers.DESTINATION_CREATE_PROJECT_ROUTE
import presentation.projects.ProjectsView


const val DESTINATION_PROJECTS_ROUTE = "projects"

@ExperimentalResourceApi
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
            onToggleTask = {

            },
            uiState = uiState,
            navigateToCreateProject = {
                navController.navigate(DESTINATION_CREATE_PROJECT_ROUTE)
            }
        )

    }
}



