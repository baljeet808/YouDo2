package presentation.create_task

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.create_task.helpers.CreateTaskViewModel


const val DestinationCreateTaskRoute = "create_task/{projectId}/{userId}"


@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addCreateTaskViewDestination(
    navController: NavController
){

    composable(
        route = DestinationCreateTaskRoute,
        arguments = listOf(
            navArgument("projectId"){
                type = NavType.StringType
            },
            navArgument("userId"){
                type = NavType.StringType
            }
        )
    ){ backStackEntry ->

        val projectId = backStackEntry.arguments?.getString("projectId")
        val userId = backStackEntry.arguments?.getString("userId")

        val viewModel = koinViewModel<CreateTaskViewModel>()
        val uiState = viewModel.uiState

        LaunchedEffect(key1 = uiState.success){
            if(uiState.success){
                navController.popBackStack()
            }
        }

        UpsertTaskView(
            onScreenEvent = {
                viewModel.onScreenEvent(it)
            },
            navigateBack = {
                navController.popBackStack()
            },
            uiState = uiState,
            getData = {
                projectId?.let {
                    userId?.let {
                        viewModel.getScreenData(projectId = projectId, userId = userId)
                    }
                }
            }
        )

    }


}
