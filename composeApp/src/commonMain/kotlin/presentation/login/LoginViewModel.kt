package presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.use_cases.auth_use_cases.LoginUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(LoginUIState())
        private set

    private val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        println("LoginViewModel: Error ${throwable.message}")
        uiState = uiState.copy(error = "Invalid credentials. Please try again!")
    }

    private var job: Job? = null

    private fun launchWithCatchingException(block : suspend CoroutineScope.() -> Unit){
        job = viewModelScope.launch(
            context = coroutineContext,
            block = block
        )
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        uiState = LoginUIState()
    }

    private fun onLogin(email: String, password: String){
        launchWithCatchingException {
            loginUseCase(email, password)
        }
    }


    fun onEvent(event : LoginScreenEvents){
        when(event){
            is LoginScreenEvents.OnAttemptToLogin -> {
                if(isEmailValid(event.email).not()){
                    uiState = uiState.copy(emailInValid = true, enableLoginButton = false)
                    return
                }
                if(isPasswordValid(event.password).not()){
                    uiState = uiState.copy(passwordInValid = true, enableLoginButton = false)
                    return
                }
                uiState = uiState.copy(isAuthenticating = true)
                onLogin(email = event.email, password = event.password)
                uiState = uiState.copy(isAuthenticating = false)
                validateLogin()
            }
            is LoginScreenEvents.OnEmailChange -> {
                uiState = uiState.copy(email = event.email, emailInValid = false, enableLoginButton = ( isEmailValid(event.email) && isPasswordValid(uiState.email) ))
            }
            is LoginScreenEvents.OnPasswordChange -> {
                uiState = uiState.copy(password = event.password, passwordInValid = false, enableLoginButton = ( isPasswordValid(event.password) && isEmailValid(uiState.email) ))
            }
            is LoginScreenEvents.OnOnboardingPageNumberChanged -> {
                uiState = uiState.copy(showLoginForm = (event.pageNumber == 2))
            }
            is LoginScreenEvents.OnRefreshUIState -> {
                uiState = LoginUIState()
            }
        }
    }

    private fun validateLogin () {
        Firebase.auth.currentUser?.let {
            uiState = uiState.copy(error = null, loginSuccessful = true)
        }?: {
            uiState = uiState.copy(error = "Invalid credentials. Please try again!")
        }
    }

    private fun isEmailValid(email : String) : Boolean{
        if(email.isBlank()){
            return false
        }
        if(email.length < 6){
            return false
        }
        if(email.none { char -> char == '@' }  || email.none { char -> char == '.' }){
            return false
        }
        return true
    }

    private fun isPasswordValid(email : String) : Boolean{
        if(email.isBlank()){
            return false
        }
        if(email.length < 5){
            return false
        }
        if(email.none { char -> char.isDigit() || char.isUpperCase() || !char.isLetterOrDigit() }){
            return false
        }
        return true
    }

}