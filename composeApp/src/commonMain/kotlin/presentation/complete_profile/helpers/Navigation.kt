package presentation.complete_profile.helpers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import domain.dto_helpers.DataError
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.complete_profile.CompleteProfileScreen

const val DESTINATION_COMPLETE_PROFILE_ROUTE = "complete_profile"

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addCompleteProfileDestination(
    navController: NavController,
    showErrorAlertDialog : (error : DataError.Network?) -> Unit = {},
    retryApiCall : MutableState<Boolean> = mutableStateOf(false)
){
    composable(
        route = DESTINATION_COMPLETE_PROFILE_ROUTE
    ){
        val viewModel = koinViewModel<CompleteProfileViewModel>()
        val uiState = viewModel.uiState


        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect (key1 = retryApiCall.value) {
            if(retryApiCall.value){
                viewModel.attemptSaveProfile()
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if( uiState.error != null ){
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to login screen if profile is saved successfully
        LaunchedEffect(key1 = uiState.savedSuccessfully){
            if(uiState.savedSuccessfully){
                navController.popBackStack()
            }
        }

        CompleteProfileScreen(
            uiState = uiState,
            updateName = {
                viewModel.updateName(it)
            },
            updateAvatarUrl = {
                viewModel.updateAvatarUrl(it)
            },
            attemptSaveProfile = {
                viewModel.attemptSaveProfile()
            },
            skip = {
                navController.popBackStack()
            }
        )

    }
}