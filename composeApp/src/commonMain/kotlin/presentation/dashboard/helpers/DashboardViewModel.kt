package presentation.dashboard.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import data.local.mappers.toUserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.models.Project
import domain.models.User
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.auth_use_cases.SignOutUseCase
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetAllProjectsAsFlowUseCase
import domain.use_cases.project_use_cases.GetProjectsUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.user_use_cases.GetUserByIdAsFlowUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase,
    private val getUserByIdAsFlowUseCase: GetUserByIdAsFlowUseCase,
    private val upsertUserUseCase: UpsertUserUseCase,
    private val getAllProjectsAsFlowUseCase: GetAllProjectsAsFlowUseCase
) : ViewModel(), KoinComponent{

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var uiState by mutableStateOf(DashboardUIState())
        private set

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

    private suspend fun hideLoading(){
        withContext(Dispatchers.Main){
            uiState = uiState.copy(isLoading = false, error = null)
        }
    }

    private fun fetchUserId() = viewModelScope.launch(Dispatchers.IO){
        dataStoreRepository.userIdAsFlow().collect {
            liveSyncCurrentUserFromFirebase(uid = it)
            liveSyncAllProjectsFromFirebase(userId = it)
            launch { getUserFromLocalDB(userId = it) }
            launch { getAllLocalProjects() }
            launch { hideLoading() }
        }
    }


    private fun liveSyncCurrentUserFromFirebase(uid : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("users").document(uid).snapshots.collect{ documentSnapshot ->
                val user = documentSnapshot.data<User>()
                launch { upsertUserLocally(user) }
                updateUserState(user)
            }
        }catch (e : Exception){
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private suspend fun upsertUserLocally(user : User){
        upsertUserUseCase(listOf(user.toUserEntity()))
    }

    private suspend fun updateUserState(currentUser : User) {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(
                currentUser = currentUser
            )
        }
    }

    private fun liveSyncAllProjectsFromFirebase(userId : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("projects")
                .where { "ownerId" equalTo userId }
                .snapshots.collect{ projectsQuerySnapshot ->
                    val projects = projectsQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Project>()
                    }
                    updateProjectsLocally(projects)
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun updateProjectsLocally(projects : List<Project>) = viewModelScope.launch(Dispatchers.IO){
        val localProjects = getProjectsUseCase()
        val projectsToDelete = localProjects.filter { localProject ->
            projects.none { project ->
                project.id == localProject.id
            }
        }
        projectsToDelete.forEach {
            deleteProjectUseCase(it)
        }
        upsertProjectUseCase(projects.map { it.toProjectEntity() })
    }


    private suspend fun getUserFromLocalDB(userId : String) {
        getUserByIdAsFlowUseCase(userId = userId).collect { userEntity ->
            userEntity?.let {
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        userId = userEntity.id,
                        userName = userEntity.name,
                        userEmail = userEntity.email,
                        userAvatarUrl = userEntity.avatarUrl
                    )
                }
            }
        }
    }

    private suspend fun getAllLocalProjects(){
        getAllProjectsAsFlowUseCase().collect {
            withContext(Dispatchers.Main){
                uiState = uiState.copy(projects = it.map { it.toProject() })
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