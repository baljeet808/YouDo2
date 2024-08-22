package presentation.project.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import domain.dto_helpers.DataError
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.project.ProjectView


const val DESTINATION_PROJECT_ROUTE = "project"
const val DESTINATION_PROJECT_ROUTE_RELATIVE_PATH = "/{projectId}"


@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addProjectViewDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
) {

    composable(
        route = DESTINATION_PROJECT_ROUTE.plus(DESTINATION_PROJECT_ROUTE_RELATIVE_PATH),
        arguments = listOf(
            navArgument("projectId") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->

        val projectId = backStackEntry.arguments?.getString("projectId")

        val viewModel = koinViewModel<ProjectViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                projectId?.let {
                    viewModel.fetchScreenData(projectId)
                }
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        ProjectView(
            fetchScreenData = {
                projectId?.let {
                    viewModel.fetchScreenData(projectId)
                }
            },
            uiState = uiState,
            onToggle = { task ->
                val updatedTask = task.task.copy()
                updatedTask.done = task.task.done.not()
            },
            navigateToCreateTask = {
                navController.navigate(
                    "create_task/".plus(projectId)
                )
            },
            deleteTask = { task ->

            },
            deleteProject = {

            },
            upsertProject = { updatedProject ->
            },
            onClickInvite = {
            },
            navigateToEditTask = {
            },
            navigateToChat = {

            },
            updateTaskTitle = { task, title ->

            }
        )

    }


}



