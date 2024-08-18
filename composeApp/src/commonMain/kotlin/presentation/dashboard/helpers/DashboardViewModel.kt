package presentation.dashboard.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.models.User
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.auth_use_cases.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardViewModel(
    private val signOutUseCase: SignOutUseCase,
) : ViewModel(), KoinComponent{

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var uiState by mutableStateOf(DashboardUIState())
        private set

    init {
        getCurrentUser()
    }

    fun getCurrentUser(){
        showLoading()
        fetchUserId()
    }
    fun attemptLogout(){
        showLoading()
        signOut()
    }

    private fun showLoading(){
        uiState = uiState.copy(isLoading = true, error = null)
    }

    private fun fetchUserId() = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.userIdAsFlow().collect {
            fetchCurrentUser(uid = it)
        }
    }


    private fun fetchCurrentUser(uid : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("users").document(uid).snapshots.collect{ documentSnapshot ->
                val user = documentSnapshot.data<User>()
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userId = user.id,
                        userName = user.name,
                        userEmail = user.email,
                        userAvatarUrl = user.avatarUrl,
                        isLoading = false
                    )
                }
            }
        }catch (e : Exception){
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
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
                    dataStoreRepository.saveIsUserLoggedIn(false)
                    dataStoreRepository.saveUserId("")
                    dataStoreRepository.saveUserName("")
                    dataStoreRepository.saveUserEmail("")
                    dataStoreRepository.saveUserAvatar("")
                    withContext(Dispatchers.Main){
                       uiState = uiState.copy(isLoading = false, error = null, isLoggedOut = true)
                    }
                }
            }
        }
    }
}