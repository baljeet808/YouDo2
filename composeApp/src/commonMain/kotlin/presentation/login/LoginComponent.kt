package presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Navigation component for login screen
 * **/
class LoginComponent(
    componentContext: ComponentContext,
    private val onNavigateToDesktop: () -> Unit
): ComponentContext by componentContext {

    fun onEvent(event: LoginNavigationEvents) {
        when (event) {
            LoginNavigationEvents.NavigateToDashboard -> onNavigateToDesktop()
        }
    }
}


sealed interface LoginNavigationEvents {
    data object NavigateToDashboard : LoginNavigationEvents
}