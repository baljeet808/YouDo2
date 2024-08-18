package presentation.complete_profile.helpers

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
import presentation.complete_profile.CompleteProfileScreen

const val DESTINATION_COMPLETE_PROFILE_ROUTE = "complete_profile"
const val DESTINATION_COMPLETE_PROFILE_ROUTE_RELATIVE_PATH = "/{uid}/{email}/{navigatedFromSignup}"


@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addCompleteProfileDestination(
    navController: NavController,
    showErrorAlertDialog: (error: DataError.Network?) -> Unit = {},
    retryApiCall: MutableState<Boolean> = mutableStateOf(false)
) {
    composable(
        route = DESTINATION_COMPLETE_PROFILE_ROUTE.plus(DESTINATION_COMPLETE_PROFILE_ROUTE_RELATIVE_PATH),
        arguments = listOf(
            navArgument("uid") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("email") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("navigatedFromSignup") {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) { backStackEntry ->


        val uid = backStackEntry.arguments?.getString("uid")
        val email = backStackEntry.arguments?.getString("email")
        val navigatedFromSignup = backStackEntry.arguments?.getBoolean("navigatedFromSignup") ?: false

        val viewModel = koinViewModel<CompleteProfileViewModel>()
        val uiState = viewModel.uiState

        //retry api call triggers when user clicks retry button in error dialog
        LaunchedEffect(key1 = retryApiCall.value) {
            if (retryApiCall.value) {
                if(uid != null && email != null){
                    viewModel.attemptSaveProfile(uid =uid, email = email)
                }else{
                    showErrorAlertDialog(DataError.Network.FORM_NOT_VALID)
                }
            }
        }

        //show error dialog if error is not null
        LaunchedEffect(key1 = uiState.error != null) {
            if (uiState.error != null) {
                showErrorAlertDialog(uiState.error)
            }
        }

        //take user to login screen if profile is saved successfully
        LaunchedEffect(key1 = uiState.savedSuccessfully) {
            if (uiState.savedSuccessfully) {
                navController.popBackStack()
            }
        }

        CompleteProfileScreen(
            title = if(navigatedFromSignup) "Complete Your \nProfile" else "Update Your \nProfile",
            uiState = uiState,
            updateName = {
                viewModel.updateName(it)
            },
            updateAvatarUrl = {
                viewModel.updateAvatarUrl(it)
            },
            attemptSaveProfile = {
                if(uid != null && email != null){
                    viewModel.attemptSaveProfile(uid = uid, email = email)
                }else{
                    showErrorAlertDialog(DataError.Network.FORM_NOT_VALID)
                }
            },
            skip = {
                navController.popBackStack()
            }
        )

    }
}