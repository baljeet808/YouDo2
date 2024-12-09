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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import presentation.project.ProjectView


const val DESTINATION_PROJECT_ROUTE = "project"
const val DESTINATION_PROJECT_ROUTE_RELATIVE_PATH = "/{projectId}/{userId}"

@ExperimentalResourceApi
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
            },
            navArgument("userId") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->

        val projectId = backStackEntry.arguments?.getString("projectId")
        val userId = backStackEntry.arguments?.getString("userId")

        val viewModel = koinViewModel<ProjectViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                projectId?.let {
                    viewModel.fetchScreenData(projectID = projectId, userId = userId!!)
                }
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        LaunchedEffect(key1 = uiState.projectDeleted){
            if(uiState.projectDeleted){
                navController.popBackStack()
            }
        }

        ProjectView(
            fetchScreenData = {
                projectId?.let {
                    viewModel.fetchScreenData(projectID = projectId, userId = userId!!)
                }
            },
            uiState = uiState,
            navigateToCreateTask = {
                navController.navigate(
                    "create_task/${projectId}/${userId}"
                )
            },
            navigateToEditTask = {
            },
            navigateToChat = {
                navController.navigate(
                    "messages/${projectId}/${userId}"
                )
            },
            onEvent = {
                viewModel.onEvent(it)
            },
            navigateBack = {
                navController.popBackStack()
            }
        )

    }


}



