package presentation.shared.userManager.helper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.getRole
import data.local.mappers.toProjectEntity
import data.local.mappers.toUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.models.Project
import domain.models.User
import domain.models.getAllIds
import domain.use_cases.user_use_cases.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ProjectUsersManagerViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(ProjectUsersManagerUIState())
        private set

    fun onEvent(event : ProjectUsersManagerScreenEvent) {
        when(event) {
            ProjectUsersManagerScreenEvent.OnClickedAddNewButton -> {
                uiState = uiState.copy(
                    showAddNewBox = true
                )
            }
            ProjectUsersManagerScreenEvent.OnClickedDismissAddNewBox -> {
                uiState = uiState.copy(
                    showAddNewBox = false
                )
            }
            is ProjectUsersManagerScreenEvent.OnClickedSubmitCodeButton -> {
                validateSharingCode(event.sharingCode)
            }
            is ProjectUsersManagerScreenEvent.OnRecentUserCheckChanged -> {
                val role = getRole(project = uiState.project.toProjectEntity(), userId = event.user.id)
                if(!event.isChecked){
                    if(role == EnumRoles.Editor) {
                        val projectCopy = uiState.project.copy(
                            collaboratorIds = uiState.project.collaboratorIds + event.user.id
                        )
                        updateProject(projectCopy)
                    }else if(role == EnumRoles.Viewer){
                        val projectCopy = uiState.project.copy(
                            viewerIds = uiState.project.viewerIds + event.user.id
                        )
                        updateProject(projectCopy)
                    }
                }else{
                    if(role == EnumRoles.Editor) {
                        val projectCopy = uiState.project.copy(
                            collaboratorIds = uiState.project.collaboratorIds - event.user.id
                        )
                        updateProject(projectCopy)
                    }else if(role == EnumRoles.Viewer){
                        val projectCopy = uiState.project.copy(
                            viewerIds = uiState.project.viewerIds - event.user.id
                        )
                        updateProject(projectCopy)
                    }
                }
            }
            is ProjectUsersManagerScreenEvent.OnSharingCodeUpdated -> {
                uiState = uiState.copy(
                    newUserSharingCode = event.sharingCode
                )
            }
            is ProjectUsersManagerScreenEvent.OnUserRolesChanged -> {

            }
        }
    }

    fun getUsers(project : Project) {
        uiState = uiState.copy(
            isLoading = true,
            project = project
        )
        viewModelScope.launch(Dispatchers.IO) {
            val users = getUsersUseCase()
            uiState = uiState.copy(
                collaborators = users.map { it.toUser() },
                isLoading = false,
                projectUsers = users.map { it.toUser() }.filter { user ->
                    project.getAllIds().contains(user.id)
                }
            )
        }
    }

    private fun validateSharingCode(sharingCode : String){
        viewModelScope.launch(Dispatchers.IO) {
           val user = Firebase.firestore.collection("users")
                .where { "sharingCode" equalTo  sharingCode }
                .get().documents.map {
                    it.data<User>()
                }
            if(user.isNotEmpty()){
                val projectCopy = uiState.project.copy(
                    collaboratorIds = uiState.project.collaboratorIds + user.first().id
                )
                updateProject(projectCopy)
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        isLoading = false,
                        showAddNewBox = false
                    )
                }
            }else{
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        isLoading = false,
                        showSharingCodeError = false
                    )
                }
            }
        }
    }

    private fun updateProject(projectCopy : Project){
        viewModelScope.launch(Dispatchers.IO) {
            Firebase.firestore.collection("projects")
                .document(uiState.project.id)
                .set(projectCopy)
        }
    }

}

