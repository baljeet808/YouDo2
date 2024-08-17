package presentation.complete_profile.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.dto_helpers.Result
import domain.use_cases.auth_use_cases.UpdateCurrentUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CompleteProfileViewModel(
    private val updateCurrentUserUseCase: UpdateCurrentUserUseCase,
) : ViewModel(), KoinComponent{

    var uiState by mutableStateOf(CompleteProfileUIState())
        private set

    fun updateName(name : String){
        uiState = uiState.copy(userName = name)
    }
    fun updateAvatarUrl(url : String){
        uiState = uiState.copy(selectedAvatar = url)
    }

    fun attemptSaveProfile(){
        uiState = uiState.copy( isLoading = true)
        updateUser()
    }

    private fun updateUser() = viewModelScope.launch(Dispatchers.IO){
        updateCurrentUserUseCase(name = uiState.userName, avatarUrl = uiState.selectedAvatar).collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main){
                       uiState = uiState.copy(isLoading = false, error = it.error, savedSuccessfully = false)
                    }
                }
                is Result.Success -> {
                    withContext(Dispatchers.Main){
                        uiState = uiState.copy(isLoading = false, error = null, savedSuccessfully = true)
                    }
                }
            }
        }
    }


}