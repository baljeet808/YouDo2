import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.login.helpers.DESTINATION_LOGIN_ROUTE
import presentation.login.helpers.addLoginDestination
import presentation.signup.helpers.addSignupDestination


@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    val navController = rememberNavController()

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
             NavHost(
                 navController = navController,
                 startDestination = DESTINATION_LOGIN_ROUTE
             ){
                 addLoginDestination(navController)
                 addSignupDestination(navController)
             }
        }
    }
}