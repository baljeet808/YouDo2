package presentation.login.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.use_cases.auth_use_cases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(LoginUIState())
        private set

    fun attemptLogin(email : String, password : String){
        if(!isEmailValid(email)){
            uiState = uiState.copy(emailInValid = true, enableLoginButton = false)
            return
        }
        if(!isPasswordValid(password)){
            uiState = uiState.copy(passwordInValid = true, enableLoginButton = false)
            return
        }
        uiState = uiState.copy(emailInValid = false, passwordInValid = false, isLoading = true)

    }

    private fun login() = viewModelScope.launch(Dispatchers.IO){

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