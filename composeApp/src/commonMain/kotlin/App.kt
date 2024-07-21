import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.dashboard.DashboardScreen
import presentation.login.LoginNavigationEvents
import presentation.login.LoginScreen
import presentation.login.LoginScreenEvents
import presentation.login.LoginViewModel


@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(
    root : RootComponent,
    prefs : DataStore<Preferences>
) {
    MaterialTheme {

        val loginViewModel = koinViewModel<LoginViewModel>()

        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ){ child ->
            when(val instance = child.instance){
                is RootComponent.Child.Login -> {
                    LoginScreen(
                        navigateToDashboard = { instance.component.onEvent(LoginNavigationEvents.NavigateToDashboard) },
                        onEvents = { event -> loginViewModel.onEvent(event) },
                        uiState = loginViewModel.uiState
                    )
                }
                is RootComponent.Child.Dashboard -> DashboardScreen(instance.component)
            }
        }
    }
}