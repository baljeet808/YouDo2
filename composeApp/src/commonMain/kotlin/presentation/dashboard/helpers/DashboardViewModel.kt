package presentation.dashboard.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.dto_helpers.Result
import domain.use_cases.auth_use_cases.GetCurrentUserUseCase
import domain.use_cases.auth_use_cases.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class DashboardViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(DashboardUIState())
        private set

    init {
        getCurrentUser()
    }

    fun getCurrentUser(){
        showLoading()
        fetchCurrentUser()
    }
    fun attemptLogout(){
        showLoading()
        signOut()
    }

    private fun showLoading(){
        uiState = uiState.copy(isLoading = true, error = null)
    }

    private fun fetchCurrentUser() = viewModelScope.launch(Dispatchers.IO){
        getCurrentUserUseCase().collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                       uiState = uiState.copy(isLoading = false, error = it.error)
                    }
                }
                is Result.Success -> {
                    it.data.let { user ->
                        withContext(Dispatchers.Main){
                           uiState = uiState.copy(
                                isLoading = false,
                                error = null,
                                userId = user.id,
                                userEmail = user.email,
                                userName = user.name,
                                userAvatarUrl = user.avatarUrl
                            )
                        }
                    }
                }
            }
        }
    }

    private fun signOut() = viewModelScope.launch(Dispatchers.IO){
        signOutUseCase().collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                       uiState = uiState.copy(isLoading = false, error = it.error, isLoggedOut = false)
                    }
                }
                is Result.Success -> {
                    withContext(Dispatchers.Main){
                       uiState = uiState.copy(isLoading = false, error = null, isLoggedOut = true)
                    }
                }
            }
        }
    }
}