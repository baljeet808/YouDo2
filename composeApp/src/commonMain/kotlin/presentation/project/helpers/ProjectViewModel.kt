package presentation.project.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.getRole
import data.local.entities.TaskEntity
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import data.local.mappers.toTask
import data.local.mappers.toTaskEntity
import data.local.mappers.toUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.Task
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.GetProjectTasksAsFlowUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import domain.use_cases.user_use_cases.GetUsersByIdsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ProjectViewModel(
    private val upsertDoToosUseCase: UpsertTasksUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val deleteDoToosUseCase: DeleteTaskUseCase,
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val getProjectTasksAsFlowUseCase: GetProjectTasksAsFlowUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(ProjectScreenState())
        private set

    private var projectsReference = Firebase.firestore
        .collection("projects")


    private suspend fun showLoading(userId: String) {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(
                isLoading = true,
                error = null,
                userId = userId
            )
        }
    }

    private suspend fun hideLoading() {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(isLoading = false, error = null)
        }
    }


    fun onEvent(event: ProjectScreenEvent){
        when(event){
            is ProjectScreenEvent.DeleteProject -> {
                uiState = uiState.copy(showProjectDetail = uiState.showProjectDetail.not())
                deleteProjectOnServer(event.project.id)
                deleteProjectLocally(event.project)
            }
            is ProjectScreenEvent.DeleteTask -> {
                deleteTaskOnServer(event.task, uiState.project)
                val projectCopy = uiState.project.copy(numberOfTasks = --uiState.project.numberOfTasks)
                updateProjectOnSever(projectCopy)
                deleteTaskLocally(event.task.toTaskEntity())
            }
            is ProjectScreenEvent.UpdateProject -> {
                updateProjectOnSever(event.project)
            }
            is ProjectScreenEvent.UpdateTask -> {
                updateTaskOnServer(event.task, uiState.project)
            }
            is ProjectScreenEvent.ToggleTask -> {
                updateTaskOnServer(event.task.copy(done = event.task.done.not()), uiState.project)
            }
            is ProjectScreenEvent.ToggleProjectDetail -> {
                uiState = uiState.copy(showProjectDetail = uiState.showProjectDetail.not())
            }
            is ProjectScreenEvent.ExitProject -> {
                if(uiState.role == EnumRoles.Viewer){
                    val viewersIds = uiState.project.viewerIds.toCollection(ArrayList())
                    viewersIds.remove(uiState.userId)
                    val projectCopy = uiState.project.copy(
                        viewerIds = viewersIds.toList()
                    )
                    updateProjectOnSever(projectCopy)
                }else if(uiState.role == EnumRoles.Editor){
                    val editorsIds = uiState.project.collaboratorIds.toCollection(ArrayList())
                    editorsIds.remove(uiState.userId)
                    val projectCopy = uiState.project.copy(
                        collaboratorIds = editorsIds.toList()
                    )
                    updateProjectOnSever(projectCopy)
                }
                uiState = uiState.copy(projectDeleted = true)
            }
        }
    }

    fun fetchScreenData(projectID: String, userId: String) = viewModelScope.launch(Dispatchers.IO) {
        launch { showLoading(userId = userId) }
        launch { syncTasksForProject(projectID = projectID) }
        launch { getProjectById(projectID = projectID) }
        launch { getProjectTasks(projectID = projectID) }
        launch { hideLoading() }
    }

    private suspend fun getUserProfiles(project: Project) {
        try{
            val userIds = project.viewerIds + project.collaboratorIds + project.ownerId
            val users = getUsersByIdsUseCase(ids = userIds)
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(users = users.map { it.toUser() })
            }
        }
        catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private suspend fun syncTasksForProject(projectID: String) {
        try {
            Firebase.firestore.collection("projects")
                .document(projectID)
                .collection("tasks")
                .snapshots.collect{ tasksQuerySnapshot ->
                    val tasks = tasksQuerySnapshot.documents.map { documentSnapshot ->
                        documentSnapshot.data<Task>()
                    }
                    upsertDoToosUseCase(tasks.map { it.toTaskEntity() })
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private suspend fun getProjectById(projectID: String) {
        try {
            getProjectByIdAsFlowUseCase(projectID).collect { project ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(project = project.toProject(), role = getRole(project = project, userId = uiState.userId))
                }
                getUserProfiles(project.toProject())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private suspend fun getProjectTasks(projectID: String) {
        try{
            getProjectTasksAsFlowUseCase(projectID).collect{ tasks ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(tasks = tasks.map { it.toTask() })
                }
            }
        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
            e.printStackTrace()
        }
    }

    private fun updateTaskOnServer(task: Task, project: Project) =
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .collection("tasks")
                .document(task.id)
                .set(task)
        }



    private fun updateProjectOnSever(project: Project) = viewModelScope.launch(Dispatchers.IO) {
        projectsReference
            .document(project.id)
            .set(project)
    }


    private fun deleteProjectOnServer(projectID: String) = viewModelScope.launch(Dispatchers.IO) {
        projectsReference
            .document(projectID)
            .delete()
    }

    private fun deleteProjectLocally(project: Project) = viewModelScope.launch(Dispatchers.IO) {
        deleteProjectUseCase(project = project.toProjectEntity())
    }

    private fun deleteTaskOnServer(task: Task, project: Project) =
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .collection("tasks")
                .document(task.id)
                .delete()
        }

    private fun deleteTaskLocally(task: TaskEntity)= viewModelScope.launch(Dispatchers.IO) {
        deleteDoToosUseCase(task)
    }
}

