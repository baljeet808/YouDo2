package presentation.shared.projectCardWithProfiles.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.EnumRoles
import common.getRole
import data.local.entities.UserEntity
import data.local.mappers.toProject
import data.local.mappers.toProjectEntity
import data.local.mappers.toUser
import data.local.mappers.toUserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.User
import domain.use_cases.project_use_cases.GetProjectByIdAsFlowUseCase
import domain.use_cases.user_use_cases.GetUsersByIdsUseCase
import domain.use_cases.user_use_cases.UpsertUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ProjectCardWithProfilesViewModel(
    private val getProjectByIdAsFlowUseCase: GetProjectByIdAsFlowUseCase,
    private val upsertUserUseCase: UpsertUserUseCase,
    private val getUsersByIdsUseCase: GetUsersByIdsUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(ProjectCardWithProfilesUIState())
        private set

    fun onEvent(event : ProjectCardWithProfilesEvent) {
        when(event) {
            ProjectCardWithProfilesEvent.OnToggleDetailVisibility -> {
                uiState = uiState.copy(
                    showProjectDetail = !uiState.showProjectDetail
                )
            }
            ProjectCardWithProfilesEvent.OnTogglePermissionDialogVisibility -> {
                uiState = uiState.copy(
                    showViewerPermissionDialog = !uiState.showViewerPermissionDialog
                )
            }
            is ProjectCardWithProfilesEvent.OnFetchUIData -> {
                //assign initial states of screen
                uiState = uiState.copy(
                    showProjectDetail = event.showProjectDetailInitially,
                    project = event.project,
                    isLoadingUsers = true,
                    currentUser = event.currentUser
                )

                //get user roles in this project and update the ui accordingly
                val role = getRole(project = event.project.toProjectEntity(), userId = event.currentUser.id)
                uiState = if(role == EnumRoles.ProAdmin || role == EnumRoles.Admin){
                    uiState.copy(
                        userRoleInProject = role,
                        projectAdmin = event.currentUser
                    )
                }else{
                    uiState.copy(
                        userRoleInProject = role
                    )
                }

                //get project and users details
                getProjectAndUsers(
                    project = event.project,
                    currentUser = event.currentUser
                )
            }
            ProjectCardWithProfilesEvent.OnRequestToOpenProjectDescriptionEditBox -> {
                uiState = if(uiState.userRoleInProject == EnumRoles.ProAdmin || uiState.userRoleInProject == EnumRoles.Admin){
                    uiState.copy(
                        showEditDescriptionBox = true
                    )
                }else{
                    uiState.copy(
                        showViewerPermissionDialog = true
                    )
                }
            }
            ProjectCardWithProfilesEvent.OnToggleDescriptionEditBoxVisibility -> {
                uiState = uiState.copy(
                    showEditDescriptionBox = !uiState.showEditDescriptionBox
                )
            }
            is ProjectCardWithProfilesEvent.OnSaveProjectDescriptionChange -> {
                uiState.project?.let { project ->
                    project.description = event.description
                    updateProjectInFirebase(project)
                }
            }

            ProjectCardWithProfilesEvent.OnRequestToOpenProjectNameEditBox -> {
                uiState = if(uiState.userRoleInProject == EnumRoles.ProAdmin || uiState.userRoleInProject == EnumRoles.Admin){
                    uiState.copy(
                        showEditTitleBox = true
                    )
                }else{
                    uiState.copy(
                        showViewerPermissionDialog = true
                    )
                }
            }
            ProjectCardWithProfilesEvent.OnToggleTitleEditBoxVisibility -> {
                uiState = uiState.copy(
                    showEditTitleBox = !uiState.showEditTitleBox
                )
            }
            is ProjectCardWithProfilesEvent.OnSaveProjectNameChange -> {
                uiState.project?.let { project ->
                    project.name = event.name
                    updateProjectInFirebase(project)
                }
            }
            ProjectCardWithProfilesEvent.OnRequestToExitOrDeleteProject -> {
                if(uiState.userRoleInProject == EnumRoles.ProAdmin || uiState.userRoleInProject == EnumRoles.Admin){
                    // show dialog to confirm deletion
                    uiState = uiState.copy(
                        showConfirmProjectDeletionDialog = true
                    )
                } else{
                    // show dialog to ask for confirmation to exit from project
                    uiState = uiState.copy(
                        showConfirmExitProjectDialog = true
                    )
                }
            }
            ProjectCardWithProfilesEvent.OnToggleExitProjectDialogVisibility -> {
                uiState = uiState.copy(
                    showConfirmExitProjectDialog = !uiState.showConfirmExitProjectDialog
                )
            }
            ProjectCardWithProfilesEvent.OnToggleProjectDeletionDialogVisibility -> {
                uiState = uiState.copy(
                    showConfirmProjectDeletionDialog = !uiState.showConfirmProjectDeletionDialog
                )
            }
            ProjectCardWithProfilesEvent.OnDeleteProject -> {
                uiState.project?.let {
                    //we only need to delete the project in firebase
                    //dashboard viewmodel will delete it from local db automatically
                    deleteProjectInFirebase(project = it)
                }
            }
            ProjectCardWithProfilesEvent.OnExitProject -> {
                uiState.project?.let { project ->
                    //First remove current user from viewer or collaborators
                    if(project.collaboratorIds.contains(uiState.currentUser.id)){
                        val collaboratorIds = project.collaboratorIds.toCollection(ArrayList())
                        collaboratorIds.remove(uiState.currentUser.id)
                        project.collaboratorIds = collaboratorIds.toList()
                    }
                    if(project.viewerIds.contains(uiState.currentUser.id)){
                        val viewerIds = project.viewerIds.toCollection(ArrayList())
                        viewerIds.remove(uiState.currentUser.id)
                        project.viewerIds = viewerIds.toList()
                    }
                    //Then update project on firebase
                    //that will automatically update the project in local db.
                    //Since we are collecting projects flow on dashboard
                    updateProjectInFirebase(project)
                }
            }
        }
    }

    private fun getProjectAndUsers(project : Project, currentUser : User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //start observing the project from local database
                liveSyncProjectLocally(project)
                //fetch users of this project
                getUsersOfProject(project, currentUser)
            } catch (e: Exception) {
                e.printStackTrace()
                setError(DataError.Network.ALL_OTHER)
            }
        }
    }

    //This will update the project details on ui as soon as it observe
    //any change in data for this project in local database
    private suspend fun liveSyncProjectLocally(project : Project) {
        getProjectByIdAsFlowUseCase(projectId = project.id).collectLatest{
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    project = it.toProject()
                )
            }
        }
    }

    private suspend fun getUsersOfProject(project: Project, currentUser: User) {
        withContext(Dispatchers.IO){
            //get user ids from project
            val userIds = project.collaboratorIds.toCollection(ArrayList())
            userIds.addAll(project.viewerIds)
            if(project.ownerId != currentUser.id){
                userIds.add(project.ownerId)
            }

            //fetch users from local data base first
            val usersInProject = getUsersByIdsUseCase(ids = userIds).toCollection(ArrayList())

            //check if any user is not in local data base
            val usersNotInLocalDB = userIds.filter { userId ->
                usersInProject.none { it.id == userId }
            }

            //fetch usersNotInLocalDB from firebase
            if(usersNotInLocalDB.isNotEmpty()){
                val newUserEntities = ArrayList<UserEntity>()
                Firebase.firestore.collection("users")
                    .where { "id" inArray usersNotInLocalDB }
                    .get().documents.map { documentSnapshot ->
                        val user = documentSnapshot.data<User>()
                        newUserEntities.add(user.toUserEntity())
                    }
                updateUsersLocally(newUserEntities)
                usersInProject.addAll(newUserEntities)
            }

            //store all of these users in the ui state
            updateUsersState(usersInProject.map { it.toUser() })
        }
    }

    private suspend fun updateUsersState(users : List<User>) {
        withContext(Dispatchers.Main){
            uiState = uiState.copy(
                usersInProject = users,
                projectAdmin = users.firstOrNull { u -> u.id == uiState.project?.ownerId } ?: User(),
                isLoadingUsers = false
            )
        }
    }

    private suspend fun updateUsersLocally(usersEntities: List<UserEntity>) {
        withContext(Dispatchers.IO) {
            upsertUserUseCase(usersEntities)
        }
    }

    private fun deleteProjectInFirebase(project : Project) {
        CoroutineScope(Dispatchers.IO).launch {
            Firebase.firestore.collection("projects")
                .document(project.id)
                .delete()
        }
    }

    private fun updateProjectInFirebase(project: Project) {
        CoroutineScope(Dispatchers.IO).launch {
            Firebase.firestore.collection("projects")
                .document(project.id)
                .set(project)
        }
    }

    private fun setError(error: DataError.Network) {
        uiState = uiState.copy(
            error = error,
            isLoadingProject = false,
            isLoadingUsers = false,
        )
    }
}

