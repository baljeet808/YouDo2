package presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

/**
 * Navigation component for login screen
 * **/
class LoginComponent(
    componentContext: ComponentContext,
    private val onNavigateToDesktop: () -> Unit,
): ComponentContext by componentContext {

    private var _email = MutableValue("")
    val email: Value<String> = _email

    private var _password = MutableValue("")
    val password: Value<String> = _password

    fun onEvent(event: LoginEvents) {
        when (event) {
            LoginEvents.NavigateToDashboard -> onNavigateToDesktop()
            LoginEvents.ShowError -> {
                //TODO: show error
            }
            is LoginEvents.UpdateEmail -> {
                _email.value = event.email
            }
            is LoginEvents.UpdatePassword -> {
                _password.value = event.password
            }

            LoginEvents.AttemptLogin -> {

            }
        }
    }

}


sealed interface LoginEvents {
    data object NavigateToDashboard : LoginEvents
    data object AttemptLogin : LoginEvents
    data object ShowError : LoginEvents
    data class UpdateEmail(val email: String) : LoginEvents
    data class UpdatePassword(val password: String) : LoginEvents
}