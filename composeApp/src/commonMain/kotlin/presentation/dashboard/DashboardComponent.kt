package presentation.dashboard

import com.arkivanov.decompose.ComponentContext

/**
 * Navigation component for Dashboard
 * **/
class DashboardComponent (
    componentContext: ComponentContext,
    private val onLogout: () -> Unit
) : ComponentContext by componentContext {

    fun onEvent(event: DashboardEvents) {
        when (event) {
            DashboardEvents.Logout -> onLogout()
        }
    }

}

sealed interface DashboardEvents{
    data object Logout : DashboardEvents
}