package presentation.signup

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import presentation.login.LoginNavigationEvents

/**
 * Navigation component for signup screen
 * **/
class SignupComponent(
    componentContext: ComponentContext,
    private val onNavigateLogin: () -> Unit
): ComponentContext by componentContext {

    fun onEvent(event: SignupNavigationEvents) {
        when (event) {
            SignupNavigationEvents.NavigateToLogin -> onNavigateLogin()
        }
    }
}


sealed interface SignupNavigationEvents {
    data object NavigateToLogin : SignupNavigationEvents
}