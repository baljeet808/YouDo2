package navigation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import presentation.dashboard.DashboardComponent
import presentation.login.LoginComponent
import presentation.signup.SignupComponent

/**
 * This class live as long as application lives
 * **/
class RootComponent(
    componentContext: ComponentContext,
    preferences: DataStore<Preferences>
) : ComponentContext by componentContext{

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.Login,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        configuration: Configuration,
        context: ComponentContext
    ): Child {
        return when (configuration) {
            Configuration.Dashboard -> Child.Dashboard(
                DashboardComponent(
                    componentContext = context,
                    onLogout = {
                        navigation.pop()
                    }
                )
            )
            Configuration.Login -> Child.Login(
                LoginComponent(
                    componentContext = context,
                    onNavigateToDesktop = {
                        navigation.pop()
                        navigation.pushNew(Configuration.Dashboard)
                    },
                    onNavigateToSignup = {
                        navigation.pushNew(Configuration.SignUp)
                    }
                )
            )
            Configuration.SignUp -> Child.Signup(
                SignupComponent(
                    componentContext = context,
                    onNavigateLogin = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    //routes
    sealed class Child{
        class Dashboard(val component: DashboardComponent) : Child()
        class Login(val component: LoginComponent) : Child()
        class Signup(val component: SignupComponent) : Child()
    }

    //configurations
    @Serializable
    sealed class Configuration{
        @Serializable
        data object Dashboard: Configuration()
        @Serializable
        data object Login: Configuration()

        data object SignUp: Configuration()
    }
}