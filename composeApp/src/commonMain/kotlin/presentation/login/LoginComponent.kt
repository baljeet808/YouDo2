package presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

/**
 * Navigation component for login screen
 * **/
class LoginComponent(
    componentContext: ComponentContext,
    private val onNavigateToDesktop: () -> Unit,
    private val viewModel : LoginViewModel
): ComponentContext by componentContext {

    private var _email = MutableValue("")
    val email: Value<String> = _email

    private var _password = MutableValue("")
    val password: Value<String> = _password

    private var _emailError = MutableValue(false)
    val emailError: Value<Boolean> = _emailError

    private var _passwordError = MutableValue(false)
    val passwordError: Value<Boolean> = _passwordError

    private val _isAuthenticating = MutableStateFlow(false)
    val isAuthenticating = _isAuthenticating.asStateFlow()

    //TODO: think of more better way to work with ui states, view model and navigation components


    fun onEvent(event: LoginEvents) {
        when (event) {
            LoginEvents.NavigateToDashboard -> onNavigateToDesktop()
            LoginEvents.ShowError -> {
                //TODO: show error
            }
            is LoginEvents.UpdateEmail -> {
                _email.value = event.email
                if(_email.value.isNotEmpty()){
                    _emailError.value = false
                }
            }
            is LoginEvents.UpdatePassword -> {
                _password.value = event.password
                if(_password.value.isNotEmpty()){
                    _passwordError.value = false
                }
            }

            LoginEvents.AttemptLogin -> {
                if(_email.value.isEmpty()){
                    _emailError.value = true
                    return
                }else{
                    _emailError.value = false
                }
                if(_password.value.isEmpty()){
                    _passwordError.value = true
                    return
                }else{
                    _passwordError.value = false
                }
                _isAuthenticating.value = true
                viewModel.onLogin(email.value,password.value)
                _isAuthenticating.value = false
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