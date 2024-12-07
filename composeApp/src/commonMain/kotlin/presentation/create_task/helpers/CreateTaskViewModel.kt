package presentation.create_task.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.SUGGESTION_ADD_DESCRIPTION
import common.getRandomId
import common.getRole
import common.getSampleDateInLong
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.Task
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUserByIdAsFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.core.component.KoinComponent

class CreateTaskViewModel(
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val getUserByIdAsFlowUseCase: GetUserByIdAsFlowUseCase,
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(CreateTaskUiState())
        private set

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

    fun onScreenEvent(event: CreateTaskScreenEvent) {
        when (event) {
            is CreateTaskScreenEvent.TaskNameChanged -> {
                val isNameValid = event.name.isNotBlank()
                val descriptionValid =
                    if (uiState.showDescription) uiState.taskDescription.isNotBlank() else true
                uiState = uiState.copy(
                    taskName = event.name,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }
            is CreateTaskScreenEvent.TaskDescriptionChanged -> {
                val isNameValid = uiState.taskName.isNotBlank()
                val descriptionValid =
                    if (uiState.showDescription) event.description.isNotBlank() else true
                uiState = uiState.copy(
                    taskDescription = event.description,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }
            is CreateTaskScreenEvent.ToggleDescriptionVisibility -> {
                val showDescription = !uiState.showDescription
                val isNameValid = uiState.taskName.isNotBlank()
                val descriptionValid =
                    if (showDescription) uiState.taskDescription.isNotBlank() else true
                uiState = uiState.copy(
                    showDescription = !uiState.showDescription,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }
            is CreateTaskScreenEvent.CreateTask -> {
                createTask()
            }
            is CreateTaskScreenEvent.TaskDueDateChanged -> {
                uiState = uiState.copy(
                    dueDate = event.dueDate
                )
            }
            is CreateTaskScreenEvent.TaskPriorityChanged -> {
                uiState = uiState.copy(
                    priority = event.priority
                )
            }

            is CreateTaskScreenEvent.OnSuggestionClicked -> {
                if(event.suggestion == SUGGESTION_ADD_DESCRIPTION){
                    uiState = uiState.copy(
                        showDescription = true,
                        showSuggestion = false
                    )
                }
            }
            CreateTaskScreenEvent.ToggleSuggestion -> {
                uiState = uiState.copy(
                    showSuggestion = !uiState.showSuggestion
                )
            }
        }
    }

    fun getScreenData(userId: String, projectId: String) = viewModelScope.launch(Dispatchers.IO) {
        launch { showLoading() }
        launch { fetchCurrentUser(uid = userId) }
        launch { getProject(projectId = projectId) }
        launch { hideLoading() }
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

    private suspend fun getProject(projectId : String){
        try {
            getProjectByIdAsFlowUseCase(projectId).collect {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        project = it.toProject()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    error = DataError.Network.ALL_OTHER,
                )
            }
        } finally {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }


    private var projectsReference = Firebase.firestore
        .collection("projects")


    private fun createTask() {
        val newTask = Task(
            id = getRandomId(),
            title = uiState.taskName,
            description = uiState.taskDescription,
            priority = uiState.priority.name,
            dueDate = uiState.dueDate.getExactDate().toInstant(TimeZone.currentSystemDefault())
                .toEpochMilliseconds(),
            createDate = getSampleDateInLong(),
            updatedBy = "${uiState.userName} Created this task",
            done = false,
            projectId = uiState.project?.id ?: ""
        )
        createTask(newTask)
    }

    private fun createTask(task: Task) {
        uiState.project?.let { project ->
            val role = getRole(project.toProjectEntity(), uiState.userId)
            val projectCopy = project.copy(numberOfTasks = ++project.numberOfTasks)
            if(role != EnumRoles.Blocked && role != EnumRoles.Viewer){
                updateTaskOnServer(task, projectCopy)
                updateProjectOnSever(projectCopy)
            }
        }
    }

    private fun updateTaskOnServer(task: Task, project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectsReference
                    .document(project.id)
                    .collection("tasks")
                    .document(task.id)
                    .set(task)
            } catch (e: Exception) {
                e.printStackTrace()
                val isNameValid = uiState.taskName.isNotBlank()
                val descriptionValid =
                    if (uiState.showDescription) uiState.taskDescription.isNotBlank() else true
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        error = DataError.Network.ALL_OTHER,
                        enableSaveButton = isNameValid && descriptionValid
                    )
                }
            } finally {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(isLoading = false)
                }
            }
        }
    }


    private fun updateProjectOnSever(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectsReference
                    .document(project.id)
                    .set(project)
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(success = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val isNameValid = uiState.taskName.isNotBlank()
                val descriptionValid =
                    if (uiState.showDescription) uiState.taskDescription.isNotBlank() else true
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        error = DataError.Network.ALL_OTHER,
                        enableSaveButton = isNameValid && descriptionValid
                    )
                }
            } finally {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(isLoading = false)
                }
            }
        }
    }
}