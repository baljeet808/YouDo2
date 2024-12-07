package presentation.createproject.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.SUGGESTION_ADD_DESCRIPTION
import common.SUGGESTION_SHOW_PREVIEW
import common.getRandomId
import common.getSampleDateInLong
import data.local.entities.ProjectEntity
import data.local.mappers.toProject
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.repository_interfaces.DataStoreRepository
import domain.use_cases.user_use_cases.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CreateProjectViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel(), KoinComponent {


    var uiState by mutableStateOf(CreateProjectUiState())
        private set

    fun getUserDetails(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserByIdUseCase(userId = userId)?.let { user ->
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        userId = user.id,
                        userName = user.name,
                        userEmail = user.email,
                        userAvatarUrl = user.avatarUrl
                    )
                }
            }
        }
    }

    fun onScreenEvent(event: CreateProjectScreenEvent) {
        when (event) {
            is CreateProjectScreenEvent.ProjectNameChanged -> {
                val isNameValid = event.name.isNotBlank()
                if(event.name.length > 2 && !uiState.showSuggestion){
                    uiState = uiState.copy(showSuggestion = true)
                }
                val descriptionValid =
                    if (uiState.showDescription) uiState.projectDescription.isNotBlank() else true
                uiState = uiState.copy(
                    projectName = event.name,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }

            is CreateProjectScreenEvent.ProjectDescriptionChanged -> {
                val isNameValid = uiState.projectName.isNotBlank()
                val descriptionValid =
                    if (uiState.showDescription) event.description.isNotBlank() else true
                uiState = uiState.copy(
                    projectDescription = event.description,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }

            is CreateProjectScreenEvent.ProjectColorChanged -> {
                uiState = uiState.copy(
                    projectColor = event.color.colorLongValue,
                    selectedColorName = event.color.paletteName
                )
            }

            is CreateProjectScreenEvent.ToggleDescriptionVisibility -> {
                val showDescription = !uiState.showDescription
                val isNameValid = uiState.projectName.isNotBlank()
                val descriptionValid =
                    if (showDescription) uiState.projectDescription.isNotBlank() else true
                uiState = uiState.copy(
                    showDescription = !uiState.showDescription,
                    enableSaveButton = isNameValid && descriptionValid
                )
            }

            is CreateProjectScreenEvent.ToggleColorOptions -> {
                uiState = uiState.copy(showColorOptions = !uiState.showColorOptions)
            }

            is CreateProjectScreenEvent.CreateProject -> {
                createProject()
            }

            CreateProjectScreenEvent.ToggleSuggestion -> {
                uiState = uiState.copy(showSuggestion = !uiState.showSuggestion)
            }

            is CreateProjectScreenEvent.OnSuggestionClicked -> {
                if(event.suggestion == SUGGESTION_ADD_DESCRIPTION){
                    uiState = uiState.copy(
                        showDescription = true,
                        showSuggestion = false
                    )
                }else if(event.suggestion == SUGGESTION_SHOW_PREVIEW){
                    uiState = uiState.copy(
                        showPreview = true,
                        showSuggestion = false,
                    )
                }
            }
        }
    }


    private var projectsReference = Firebase.firestore
        .collection("projects")

    private fun createProject() {
        uiState = uiState.copy(isLoading = true, enableSaveButton = false)
        val newProject = ProjectEntity(
            id = getRandomId(),
            ownerId = uiState.userId,
            name = uiState.projectName,
            ownerName = uiState.userName,
            ownerAvatarUrl = uiState.userAvatarUrl,
            description = uiState.projectDescription,
            collaboratorIds = "",
            viewerIds = "",
            update = "${uiState.userName.ifBlank { uiState.userEmail }} created new project named '${uiState.projectName}.'",
            color = uiState.projectColor,
            updatedAt = getSampleDateInLong(),
            numberOfTasks = 0
        )
        createProject(newProject)
    }


    private fun createProject(project: ProjectEntity) {
        createProjectOnServer(project)

    }

    private fun createProjectOnServer(project: ProjectEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectsReference
                    .document(project.id)
                    .set(project.toProject())
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(success = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val isNameValid = uiState.projectName.isNotBlank()
                val descriptionValid = if (uiState.showDescription) uiState.projectDescription.isNotBlank() else true
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


