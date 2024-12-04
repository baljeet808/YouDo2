package presentation.dashboard.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import data.local.mappers.toUser
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _userIds = MutableStateFlow<Set<String>>(emptySet())
    val userIds: StateFlow<Set<String>> get() = _userIds

    init {
        observeUserIds()
    }

    var uiState by mutableStateOf(DashboardUIState())
        private set


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

    fun fetchData(userId : String) = viewModelScope.launch(Dispatchers.IO){
        showLoading()
        liveSyncCurrentUserFromFirebase(uid = userId)
        getUserFromLocalDB(userId = userId)
        liveSyncAllProjectsFromFirebase(userId = userId)
        getAllLocalProjects()
        launch { hideLoading() }
    }

    //fetching current user data from firebase and updating it into local database
    private fun liveSyncCurrentUserFromFirebase(uid : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("users").document(uid).snapshots.collect{ documentSnapshot ->
                val user = documentSnapshot.data<User>()
                upsertUserUseCase(listOf(user.toUserEntity()))
            }
        }catch (e : Exception){
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = DataError.CustomException( "liveSyncCurrentUserFromFirebase : "+(e.message ?: "An Error occurred while trying to liveSyncCurrentUserFromFirebase"))
                )
            }
        }
    }

    //live sync current user from local database and updating it into ui state
    private fun getUserFromLocalDB(userId : String) = viewModelScope.launch(Dispatchers.IO){
        getUserByIdAsFlowUseCase(userId = userId).collect { userEntity ->
            userEntity?.let {
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        userId = userEntity.id,
                        userName = userEntity.name,
                        userEmail = userEntity.email,
                        userAvatarUrl = userEntity.avatarUrl,
                        currentUser = userEntity.toUser()
                    )
                }
            }
        }
    }

    //live syncing all projects from firebase
    private fun liveSyncAllProjectsFromFirebase(userId : String) = viewModelScope.launch(Dispatchers.IO){
        try {
            Firebase.firestore.collection("projects")
                .where {
                    any(
                        "ownerId" equalTo userId,
                        "collaboratorIds" contains userId,
                        "viewerIds" contains userId
                    )
                }
                .snapshots.collect{ projectsQuerySnapshot ->
                    val projects = projectsQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Project>()
                    }
                    updateUserIdsFromProjects(projects)
                    updateProjectsLocally(projects)
                }
        }catch (e : Exception){
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = DataError.CustomException( "liveSyncAllProjectsFromFirebase : "+(e.message ?: "An Error occurred while trying to liveSyncAllProjectsFromFirebase"))
                )
            }
        }
    }

    //updating those project's updates from firebase into local database
    private suspend fun updateProjectsLocally(projects : List<Project>) = withContext(Dispatchers.IO){
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

    //fetching all project from local database
    private fun getAllLocalProjects()= viewModelScope.launch(Dispatchers.IO){
        getAllProjectsAsFlowUseCase().collect {
            withContext(Dispatchers.Main){
                uiState = uiState.copy(projects = it.map { it.toProject() })
            }
        }
    }

    private fun updateUserIdsFromProjects(projects: List<Project>) {
        val newUserIds = projects.flatMap { it.collaboratorIds + it.viewerIds }.toSet()
        val currentIds = _userIds.value
        val freshIds = (currentIds + newUserIds) - (currentIds - newUserIds)
        println("TEST fresh ids: $freshIds")
        if (_userIds.value != freshIds) {
            println("TEST userIds updated")
            _userIds.value = freshIds
        } else {
            println("TEST userIds not updated")
        }
    }

    private fun observeUserIds() = viewModelScope.launch(Dispatchers.IO) {
        try {
            println("TEST observeUserIds collecting")
            userIds.collect { ids ->
                println("TEST ids observed: $ids")
                if (ids.isNotEmpty()) {
                    Firebase.firestore.collection("users")
                        .where { "id" inArray ids.toList() }
                        .snapshots.collect { documentSnapshots ->
                            val users = documentSnapshots.documents.map { it.data<User>() }
                            println("TEST users fetched: $users")
                            withContext(Dispatchers.Main) {
                                uiState = uiState.copy(allProjectUsers = users)
                            }
                            upsertUserUseCase(users.map { it.toUserEntity() })
                        }
                } else {
                    println("TEST ids is empty")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = DataError.CustomException( "observeUserIds : "+(e.message ?: "An Error occurred while trying to observeUserIds"))
                )
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