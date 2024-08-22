package presentation.project.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entities.TaskEntity
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.GetProjectTasksAsFlowUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import domain.use_cases.user_use_cases.GetUserByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUsersByIdsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectViewModel(
    private val upsertDoToosUseCase: UpsertTasksUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val deleteDoToosUseCase: DeleteTaskUseCase,
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase,
    private val getProjectTasksAsFlowUseCase: GetProjectTasksAsFlowUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase,
    private val getUserByIdAsFlowUseCase: GetUserByIdAsFlowUseCase
) : ViewModel(), KoinComponent {

    private val dataStoreRepository: DataStoreRepository by inject<DataStoreRepository>()

    var uiState by mutableStateOf(ProjectScreenState())
        private set

    private var projectsReference = Firebase.firestore
        .collection("projects")


    private fun showLoading() {
        uiState = uiState.copy(isLoading = true, error = null)
    }

    private var projectId = ""

    fun fetchScreenData(projectID: String) = viewModelScope.launch(Dispatchers.IO) {
        projectId = projectID
        showLoading()
        fetchUserId()
        getProjectById()
        getProjectTasks()
        getUserProfiles()
    }

    private fun fetchUserId() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.userIdAsFlow().collect {
            fetchCurrentUser(uid = it)
        }
    }


    private fun fetchCurrentUser(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            getUserByIdAsFlowUseCase(uid).collect { user ->
                user?.let {
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(
                            userId = user.id,
                            userName = user.name,
                            userEmail = user.email,
                            userAvatarUrl = user.avatarUrl,
                        )
                    }
                }?: kotlin.run {
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(isLoading = false, error = DataError.Network.NOT_FOUND)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }


    private fun getProjectById() = viewModelScope.launch(Dispatchers.IO) {
        try {
            getProjectByIdAsFlowUseCase(projectId).collect { project ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(project = project)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private fun getProjectTasks() = viewModelScope.launch(Dispatchers.IO) {
        try{
            getProjectTasksAsFlowUseCase(projectId).collect{ tasks ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(tasks = tasks)
                }
            }
        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private fun getUserProfiles() = viewModelScope.launch(Dispatchers.IO) {
        try{
            val userIds = uiState.project.toProject().viewerIds
            userIds.plus(uiState.project.toProject().collaboratorIds)
                .plus(uiState.project.toProject().ownerId)
            getUsersByIdsUseCase(userIds).collect { users ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(users = users, isLoading = false)
                }
            }
        }
        catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }


    private fun updateTaskOnServer(task: TaskEntity, project: Project) =
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .collection("tasks")
                .document(task.id)
                .set(task)
        }

    private fun updateTaskLocally(task: TaskEntity, project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            upsertDoToosUseCase(listOf(task))
            // updateProject(project)
        }
    }


    private fun updateProjectOnSever(project: Project) = viewModelScope.launch(Dispatchers.IO) {
        projectsReference
            .document(project.id)
            .set(project)
    }

    private fun updateProjectLocally(project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            upsertProjectUseCase(listOf(project.toProjectEntity()))
        }
    }

    private fun deleteProjectOnServer() = viewModelScope.launch(Dispatchers.IO) {
        projectsReference
            .document("")
            .delete()
    }

    private fun deleteProjectLocally(project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteProjectUseCase(project = project.toProjectEntity())
        }
    }

    private fun deleteTaskOnServer(task: TaskEntity, project: Project) =
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .collection("todos")
                .document(task.id)
                .delete()
        }

    private fun deleteTaskLocally(task: TaskEntity, project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteDoToosUseCase(task)
            //updateProject(project)
        }
    }


}

