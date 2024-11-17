package presentation.onboarding.helpers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.onboarding.OnboardingScreen

const val DESTINATION_ONBOARDING_ROUTE = "onboarding"

@ExperimentalResourceApi
@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.addOnboardingDestination(
    navController: NavController
){
    composable(
        route = DESTINATION_ONBOARDING_ROUTE
    ){
        val viewModel = koinViewModel<OnBoardingViewModel>()
        OnboardingScreen(
            moveToLogin = {
                //viewModel.setOnboardingStatus(isComplete = true)
                navController.navigate(DESTINATION_LOGIN_ROUTE){
                    popUpTo(DESTINATION_ONBOARDING_ROUTE){
                        inclusive = true
                    }
                }
            }
        )
    }
}