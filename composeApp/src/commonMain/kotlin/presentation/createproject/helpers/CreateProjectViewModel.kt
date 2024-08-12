package presentation.createproject.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.getLongValueInString
import common.getRandomId
import common.getSampleDateInLong
import data.local.entities.ProjectEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository_interfaces.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CreateProjectViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel(), KoinComponent {


    var uiState by mutableStateOf(CreateProjectUiState())
        private set

    init {
        getUserDetails()
    }

    private fun getUserDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.userIdAsFlow().collect{
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        userId = it
                    )
                }
            }
            dataStoreRepository.userNameAsFlow().collect{
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userName = it
                    )
                }
            }
            dataStoreRepository.userEmailAsFlow().collect{
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        userEmail = it
                    )
                }
            }
        }
    }

    fun onScreenEvent(event: CreateProjectScreenEvent) {
        when (event) {
            is CreateProjectScreenEvent.ProjectNameChanged -> {
                uiState = uiState.copy(projectName = event.name)
            }
            is CreateProjectScreenEvent.ProjectDescriptionChanged -> {
                uiState = uiState.copy(projectDescription = event.description)
            }
            is CreateProjectScreenEvent.ProjectColorChanged -> {
                uiState = uiState.copy(projectColor = event.color)
            }
            is CreateProjectScreenEvent.ToggleDescriptionVisibility -> {
                uiState = uiState.copy(showDescription = !uiState.showDescription)
            }
            is CreateProjectScreenEvent.ToggleColorOptions -> {
                uiState = uiState.copy(showColorOptions = !uiState.showColorOptions)
            }
            is CreateProjectScreenEvent.CreateProject -> {
                createProject()
            }
        }
    }


    private var projectsReference = Firebase.firestore
        .collection("projects")

    private fun createProject() {
        val newProject = ProjectEntity(
            id = getRandomId(),
            ownerId = uiState.userId,
            name = uiState.projectName,
            description = uiState.projectDescription,
            collaboratorIds = "",
            viewerIds = "listOf()",
            update = "${uiState.userName.ifBlank { uiState.userEmail }} created new project named '${uiState.projectName}.'",
            color = uiState.projectColor.getLongValueInString(),
            updatedAt = getSampleDateInLong()
        )
        createProject(newProject)
    }


    private fun createProject(project : ProjectEntity){
        createProjectOnServer(project)
    }

    private fun createProjectOnServer(project: ProjectEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            projectsReference
                .document(project.id)
                .set(project)
        }
    }
}


