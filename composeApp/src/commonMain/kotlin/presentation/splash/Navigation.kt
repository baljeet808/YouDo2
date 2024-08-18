package presentation.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val DESTINATION_SPLASH_ROUTE = "splash"

fun NavGraphBuilder.addSplashDestination(){
    composable(
        route = DESTINATION_SPLASH_ROUTE
    ){
        SplashScreenView()
    }
}