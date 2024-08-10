package presentation.signup.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.isEmailValid
import common.isPasswordValid
import domain.dto_helpers.Result
import domain.use_cases.auth_use_cases.SignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class SignupViewModel(
    private val signupUseCase: SignupUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(SignupUIState())
        private set

    fun attemptSignup(email: String, password: String){
        if(!email.isEmailValid()){
            uiState = uiState.copy(emailInValid = true, enableSignupButton = false)
            return
        }
        if(!password.isPasswordValid()){
            uiState = uiState.copy(passwordInValid = true, enableSignupButton = false)
            return
        }
        uiState = uiState.copy(emailInValid = false, passwordInValid = false, isLoading = true)
        signup(email = email, password = password)
    }

    private fun signup(email: String, password: String) = viewModelScope.launch(Dispatchers.IO){
        signupUseCase(email, password).collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                        uiState.copy(isLoading = false, error = it.error, signupSuccessful = false)
                    }
                }
                is Result.Success -> {
                    withContext(Dispatchers.Main){
                        uiState.copy(isLoading = false, error = null, signupSuccessful = true)
                    }
                }
            }
        }
    }

}