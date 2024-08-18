package presentation.login.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.isEmailValid
import common.isPasswordValid
import data.local.entities.UserEntity
import data.local.mappers.toUserEntity
import domain.dto_helpers.Result
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.auth_use_cases.LoginUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val upsertUserUseCase: UpsertUserUseCase
) : ViewModel(), KoinComponent{

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var uiState by mutableStateOf(LoginUIState())
        private set

    fun updatePassword(password : String){
        val passwordValid = password.isPasswordValid()
        uiState = uiState.copy(password = password, passwordInValid = passwordValid.not(), enableLoginButton = passwordValid && uiState.email.isEmailValid())
    }

    fun updateEmail(email : String){
        val emailValid = email.isEmailValid()
        uiState = uiState.copy(email = email, emailInValid = emailValid.not(), enableLoginButton = emailValid && uiState.password.isPasswordValid())
    }

    fun attemptLogin(){
        if(!uiState.email.isEmailValid()){
            uiState = uiState.copy(emailInValid = true, enableLoginButton = false)
            return
        }
        if(!uiState.password.isPasswordValid()){
            uiState = uiState.copy(passwordInValid = true, enableLoginButton = false)
            return
        }
        uiState = uiState.copy(emailInValid = false, passwordInValid = false, isLoading = true)
        login(email = uiState.email, password = uiState.password)
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
                    dataStoreRepository.saveUserId(it.data.id)
                    dataStoreRepository.saveUserName(it.data.name)
                    dataStoreRepository.saveUserEmail(it.data.email)
                    dataStoreRepository.saveUserAvatar(it.data.avatarUrl)
                    saveUserToRoomDB(it.data.toUserEntity())
                }
            }
        }
    }

    private fun saveUserToRoomDB(user : UserEntity) = viewModelScope.launch(Dispatchers.IO){
        upsertUserUseCase(listOf(user))
        withContext(Dispatchers.Main){
            uiState =uiState.copy(isLoading = false, error = null, loginSuccessful = true)
        }
    }
}