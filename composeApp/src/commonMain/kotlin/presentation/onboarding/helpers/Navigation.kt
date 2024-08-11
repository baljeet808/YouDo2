package presentation.onboarding.helpers

import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import common.hasOnboardedKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.onboarding.OnboardingScreen

const val DESTINATION_ONBOARDING_ROUTE = "onboarding"

fun NavGraphBuilder.addOnboardingDestination(
    navController: NavController,
    prefs : DataStore<Preferences>
){
    composable(
        route = DESTINATION_ONBOARDING_ROUTE
    ){
        val scope = rememberCoroutineScope()
        OnboardingScreen(
            moveToLogin = {
                scope.launch(Dispatchers.IO) {
                    prefs.edit { dataStore ->
                        dataStore[hasOnboardedKey] = true
                    }
                }
                navController.navigate(DESTINATION_LOGIN_ROUTE){
                    popUpTo(DESTINATION_ONBOARDING_ROUTE){
                        inclusive = true
                    }
                }
            }
        )
    }
}