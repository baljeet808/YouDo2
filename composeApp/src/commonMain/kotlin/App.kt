import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.dashboard.DashboardScreen
import presentation.login.LoginScreen


@Composable
@Preview
fun App(root : RootComponent) {
    MaterialTheme {
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