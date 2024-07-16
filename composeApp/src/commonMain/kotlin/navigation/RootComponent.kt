package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import presentation.dashboard.DashboardComponent
import presentation.login.LoginComponent

/**
 * This class live as long as application lives
 * **/
class RootComponent(
    componentContext: ComponentContext
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
                        navigation.pushNew(Configuration.Dashboard)
                    }
                )
            )
        }
    }

    //routes
    sealed class Child{
        class Dashboard(val component: DashboardComponent) : Child()
        class Login(val component: LoginComponent) : Child()
    }

    //configurations
    @Serializable
    sealed class Configuration{
        @Serializable
        data object Dashboard: Configuration()
        @Serializable
        data object Login: Configuration()
    }
}