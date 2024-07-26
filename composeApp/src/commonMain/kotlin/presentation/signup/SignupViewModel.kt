package presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.use_cases.auth_use_cases.SignupUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SignupViewModel(
    private val signupUseCase: SignupUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(SignupUIState())
        private set

    private val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        println("SignupViewModel: Error ${throwable.message}")
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
    }

    private fun onSignup(email: String, password: String){
        launchWithCatchingException {
            signupUseCase(email, password)
        }
    }


    fun onEvent(event : SignupScreenEvents){
        when(event){
            is SignupScreenEvents.OnEmailChange -> {
                uiState = uiState.copy(email = event.email, emailInValid = false, enableSignupButton = ( isEmailValid(event.email) && isPasswordValid(uiState.email) ))
            }
            is SignupScreenEvents.OnPasswordChange -> {
                uiState = uiState.copy(password = event.password, passwordInValid = false, enableSignupButton = ( isPasswordValid(event.password) && isEmailValid(uiState.email) ))
            }

            is SignupScreenEvents.OnAttemptToSignup -> {
                if(isEmailValid(event.email).not()){
                    uiState = uiState.copy(emailInValid = true, enableSignupButton = false)
                    return
                }
                if(isPasswordValid(event.password).not()){
                    uiState = uiState.copy(passwordInValid = true, enableSignupButton = false)
                    return
                }
                uiState = uiState.copy(isAuthenticating = true)
                onSignup(email = event.email, password = event.password)
                uiState = uiState.copy(isAuthenticating = false)
                validateSignup()
            }
        }
    }

    private fun validateSignup () {
        Firebase.auth.currentUser?.let {
            uiState = uiState.copy(error = null, signupSuccessful = true)
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