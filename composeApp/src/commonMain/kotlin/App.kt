import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.isUserLoggedInKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.dashboard.DashboardScreen
import presentation.login.LoginNavigationEvents
import presentation.login.LoginScreen
import presentation.login.LoginViewModel
import presentation.signup.SignupNavigationEvents
import presentation.signup.SignupScreen
import presentation.signup.SignupViewModel


@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(
    root: RootComponent,
    prefs: DataStore<Preferences>
) {
   // val userLoggedIn = prefs.data.map { it[isUserLoggedInKey]?: false }.collectAsState(initial = false).value

    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Login -> {
                    val loginViewModel = koinViewModel<LoginViewModel>()
                    LoginScreen(
                        navigateToDashboard = {
                            instance.component.onEvent(LoginNavigationEvents.NavigateToDashboard)
                        },
                        navigateToSignup = {
                            instance.component.onEvent(LoginNavigationEvents.NavigateToSignup)
                        },
                        onEvents = { event -> loginViewModel.onEvent(event) },
                        uiState = loginViewModel.uiState,
                        prefs = prefs
                    )
                }

                is RootComponent.Child.Dashboard -> DashboardScreen(instance.component)
                is RootComponent.Child.Signup -> {
                    val signupViewModel = koinViewModel<SignupViewModel>()
                    SignupScreen(
                        onEvents = { event -> signupViewModel.onEvent(event) },
                        uiState = signupViewModel.uiState,
                        navigateToLogin = {
                            instance.component.onEvent(SignupNavigationEvents.NavigateToLogin)
                        }
                    )
                }
            }
        }
    }
}