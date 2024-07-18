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
import presentation.dashboard.DashboardScreen
import presentation.login.LoginScreen


@Composable
@Preview
fun App(
    root : RootComponent,
    prefs : DataStore<Preferences>
) {
    MaterialTheme {

        val scope = rememberCoroutineScope()
        val auth = remember { Firebase.auth }


        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ){ child ->
            when(val instance = child.instance){
                is RootComponent.Child.Login -> LoginScreen(instance.component)
                is RootComponent.Child.Dashboard -> DashboardScreen(instance.component)
            }
        }
    }
}