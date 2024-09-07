package presentation.create_task.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.getRandomId
import common.getRole
import common.getSampleDateInLong
import data.local.entities.TaskEntity
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.project_use_cases.UpsertProjectUseCase
import domain.use_cases.task_use_cases.UpsertTasksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.core.component.KoinComponent

class CreateTaskViewModel(
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val upsertTasksUseCase: UpsertTasksUseCase,
    private val upsertProjectUseCase: UpsertProjectUseCase,
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(CreateTaskUiState())
        private set

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
        }
    }


    fun getUserDetails(projectId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.userIdAsFlow().collect {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userId = it
                    )
                }
            }
            dataStoreRepository.userNameAsFlow().collect {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userName = it
                    )
                }
            }
            dataStoreRepository.userEmailAsFlow().collect {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userEmail = it
                    )
                }
            }
            getProject(projectId)
        }
    }

    private fun getProject(projectId : String){
        viewModelScope.launch(Dispatchers.IO) {
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
    }


    private var projectsReference = Firebase.firestore
        .collection("projects")


    fun createTask() {
        val newTask = TaskEntity(
            id = getRandomId(),
            title = uiState.taskName,
            description = uiState.taskDescription,
            priority = uiState.priority.name,
            dueDate = uiState.dueDate.getExactDate().toInstant(TimeZone.currentSystemDefault())
                .toEpochMilliseconds(),
            createDate = getSampleDateInLong(),
            updatedBy = "",
            done = false,
            projectId = uiState.project?.id ?: ""
        )
        createTask(newTask)
    }

    private fun createTask(task: TaskEntity) {
        uiState.project?.let { project ->
            when (getRole(project.toProjectEntity(), uiState.userId)) {
                EnumRoles.ProAdmin -> {
                    updateTaskOnServer(task, project)
                }

                EnumRoles.Admin -> {
                    updateTaskLocally(task, project)
                }

                EnumRoles.Editor -> {
                    updateTaskOnServer(task, project)
                }

                EnumRoles.Viewer -> {
                    //Do nothing can't update anything
                    //UI handles this by itself
                }

                EnumRoles.Blocked -> {
                    //Do nothing can't update anything
                    //UI handles this by itself
                }
            }
        }
    }

    private fun updateTaskOnServer(task: TaskEntity, project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectsReference
                    .document(project.id)
                    .collection("tasks")
                    .document(task.id)
                    .set(task)
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

    private fun updateTaskLocally(task: TaskEntity, project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            upsertTasksUseCase(listOf(task))
            updateProject(project)
        }
    }


    private fun updateProject(project: Project) {
        val projectCopy = project.copy()
        projectCopy.updatedAt = getSampleDateInLong()

        when (getRole(project.toProjectEntity(), uiState.userId)) {
            EnumRoles.ProAdmin -> {
                updateProjectOnSever(project)
            }

            EnumRoles.Admin -> {
                updateProjectLocally(project)
            }

            EnumRoles.Editor -> {
                updateProjectOnSever(project)
            }

            EnumRoles.Viewer -> {
                //Do nothing can't update anything
                //UI handles this by itself
            }

            EnumRoles.Blocked -> {
                //Do nothing can't update anything
                //UI handles this by itself
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

    private fun updateProjectLocally(project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            upsertProjectUseCase(listOf(project.toProjectEntity()))
        }
    }


}