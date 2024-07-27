import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.dashboard.DashboardEvents
import presentation.dashboard.DashboardScreen
import presentation.login.LoginNavigationEvents
import presentation.login.LoginScreen
import presentation.signup.SignupNavigationEvents
import presentation.signup.SignupScreen


@Composable
@Preview
fun App(
    root: RootComponent,
    prefs: DataStore<Preferences>
) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Login -> {
                    LoginScreen(
                        navigateToDashboard = {
                            instance.component.onEvent(LoginNavigationEvents.NavigateToDashboard)
                        },
                        navigateToSignup = {
                            instance.component.onEvent(LoginNavigationEvents.NavigateToSignup)
                        },
                        prefs = prefs
                    )
                }

                is RootComponent.Child.Dashboard -> {
                    DashboardScreen(
                        navigateToLogin = {
                            instance.component.onEvent(DashboardEvents.Logout)
                        },
                        prefs = prefs
                    )
                }
                is RootComponent.Child.Signup -> {
                    SignupScreen(
                        navigateToLogin = {
                            instance.component.onEvent(SignupNavigationEvents.NavigateToLogin)
                        }
                    )
                }
            }
        }
    }
}