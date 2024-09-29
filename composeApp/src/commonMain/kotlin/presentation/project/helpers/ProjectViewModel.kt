package presentation.project.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entities.TaskEntity
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import data.local.mappers.toTask
import data.local.mappers.toTaskEntity
import data.local.mappers.toUser
import data.local.mappers.toUserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.Task
import domain.use_cases.project_use_cases.DeleteProjectUseCase
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.task_use_cases.DeleteTaskUseCase
import domain.use_cases.task_use_cases.GetProjectTasksAsFlowUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import domain.use_cases.user_use_cases.GetUserByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUsersUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
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
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserByIdAsFlowUseCase: GetUserByIdAsFlowUseCase,
    private val getProjectTasksAsFlowUseCase: GetProjectTasksAsFlowUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase,
    private val upsertUserUseCase: UpsertUserUseCase,
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(ProjectScreenState())
        private set

    private var projectsReference = Firebase.firestore
        .collection("projects")


    private suspend fun showLoading() {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(isLoading = true, error = null)
        }
    }

    private suspend fun hideLoading() {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(isLoading = false, error = null)
        }
    }

    fun fetchScreenData(projectID: String, userId: String) = viewModelScope.launch(Dispatchers.IO) {
        launch { showLoading() }
        launch { fetchCurrentUser(uid = userId) }
        launch { syncProjectWithFirebase(projectID = projectID) }
        launch { syncTasksForProject(projectID = projectID) }
        launch { getProjectById(projectID = projectID) }
        launch { getProjectTasks(projectID = projectID) }
        launch { hideLoading() }
    }

    private suspend fun syncProjectWithFirebase(projectID: String) {
        try {
            projectsReference
                .document(projectID)
                .snapshots.collect { documentSnapshot ->
                    val project = documentSnapshot.data<Project>()
                    updateProjectLocally(project)
                    getUserProfiles(project)
                }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false, error = DataError.Network.ALL_OTHER)
            }
        }
    }

    private suspend fun updateProjectLocally(project: Project) {
        upsertProjectUseCase(listOf(project.toProjectEntity()))
    }

    private suspend fun getUserProfiles(project: Project) {
        try{
            //extract ids of users collaborating on this project
            //along with the project owner
            val userIds = project.viewerIds + project.collaboratorIds + project.ownerId

            //fetch all users from database
            val localUsers = getUsersUseCase()

            //filter the users that are already in the local database
            val usersFoundFromLocalDB = localUsers.filter { user ->
                userIds.contains(user.id)
            }
            //show the users which are in local database first
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(users = usersFoundFromLocalDB.map { it.toUser() })
            }

            //filter the users that are not in the local database
            val usersIdsNotInLocalDb = userIds.filter { id ->
                localUsers.none { user ->
                    user.id == id
                }
            }

            //fetch the users from firebase which are not in the local database
            usersIdsNotInLocalDb.forEach { userId ->
                val userSnapShot = Firebase.firestore.collection("users")
                    .document(userId)
                    .get()
                val user = userSnapShot.data<domain.models.User>()
                upsertUserUseCase(listOf(user.toUserEntity()))
                //keep adding the users one by one to the uiState
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(users = usersFoundFromLocalDB.map { it.toUser() }.plus(user))
                }
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
                    updateTasksLocally(tasks = tasks)
                }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    private suspend fun updateTasksLocally(tasks: List<Task>) {
        upsertDoToosUseCase(tasks.map { it.toTaskEntity() })
    }

    private suspend fun fetchCurrentUser(uid: String) {
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


    private suspend fun getProjectById(projectID: String) {
        try {
            getProjectByIdAsFlowUseCase(projectID).collect { project ->
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(project = project.toProject())
                }
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

    private fun updateTaskOnServer(task: TaskEntity, project: Project) =
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

    private fun deleteTaskOnServer(task: TaskEntity, project: Project) =
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .collection("todos")
                .document(task.id)
                .delete()
        }

    private fun deleteTaskLocally(task: TaskEntity)= viewModelScope.launch(Dispatchers.IO) {
        deleteDoToosUseCase(task)
    }
}

