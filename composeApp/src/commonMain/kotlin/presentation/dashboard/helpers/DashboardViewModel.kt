package presentation.dashboard.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toProjectEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.models.Project
import domain.models.User
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.auth_use_cases.SignOutUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase
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
                fetchProjectsFromFireStore(user)
            }
        }catch (e : Exception){
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private fun fetchProjectsFromFireStore(user : User) = viewModelScope.launch(Dispatchers.IO){
        try {
        Firebase.firestore.collection("projects")

            .snapshots.collect{ querySnapshot ->
                val projects = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<Project>()
                }
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userId = user.id,
                        userName = user.name,
                        userEmail = user.email,
                        userAvatarUrl = user.avatarUrl,
                        isLoading = false,
                        projects = projects
                    )
                }
                upsertProjectsLocally(projects)
            }
        }catch (e : Exception){
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private fun upsertProjectsLocally(projects : List<Project>) = viewModelScope.launch(Dispatchers.IO){
        upsertProjectUseCase(projects.map { it.toProjectEntity() })
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