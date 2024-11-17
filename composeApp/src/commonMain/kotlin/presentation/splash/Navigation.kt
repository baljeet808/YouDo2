package presentation.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.ExperimentalResourceApi

const val DESTINATION_SPLASH_ROUTE = "splash"

@ExperimentalResourceApi
fun NavGraphBuilder.addSplashDestination(){
    composable(
        route = DESTINATION_SPLASH_ROUTE
    ){
        SplashScreenView()
    }
}