package presentation.login.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.isEmailValid
import common.isPasswordValid
import domain.dto_helpers.Result
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.auth_use_cases.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel(), KoinComponent{

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var uiState by mutableStateOf(LoginUIState())
        private set

    fun updateCredentials(email : String, password: String){
        uiState = uiState.copy(emailInValid = false, passwordInValid = false)
        if(email.isEmailValid() && password.isPasswordValid()){
            uiState = uiState.copy(enableLoginButton = true)
        }
    }

    fun attemptLogin(email : String, password : String){
        if(!email.isEmailValid()){
            uiState = uiState.copy(emailInValid = true, enableLoginButton = false)
            return
        }
        if(!password.isPasswordValid()){
            uiState = uiState.copy(passwordInValid = true, enableLoginButton = false)
            return
        }
        uiState = uiState.copy(emailInValid = false, passwordInValid = false, isLoading = true)
        login(email = email, password = password)
    }

    private fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO){
        loginUseCase(email, password).collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                        uiState = uiState.copy(isLoading = false, error = it.error, loginSuccessful = false)
                    }
                }
                is Result.Success -> {
                    dataStoreRepository.saveIsUserLoggedIn(true)
                    withContext(Dispatchers.Main){
                        uiState =uiState.copy(isLoading = false, error = null, loginSuccessful = true)
                    }
                }
            }
        }
    }
}