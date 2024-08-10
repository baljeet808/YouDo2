package presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.use_cases.auth_use_cases.SignupUseCase
import org.koin.core.component.KoinComponent

class SignupViewModel(
    private val signupUseCase: SignupUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(SignupUIState())
        private set

    private fun onSignup(email: String, password: String){

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