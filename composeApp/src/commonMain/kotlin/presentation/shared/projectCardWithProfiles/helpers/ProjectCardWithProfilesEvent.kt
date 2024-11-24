package presentation.shared.projectCardWithProfiles.helpers

import domain.models.Project
import domain.models.User

sealed class ProjectCardWithProfilesEvent {
    data object OnToggleDetailVisibility : ProjectCardWithProfilesEvent()
    //dialog events
    data object OnTogglePermissionDialogVisibility : ProjectCardWithProfilesEvent()
    data object OnToggleProjectDeletionDialogVisibility : ProjectCardWithProfilesEvent()
    data object OnToggleExitProjectDialogVisibility : ProjectCardWithProfilesEvent()
    //title edit field events
    data object OnRequestToOpenProjectNameEditBox : ProjectCardWithProfilesEvent()
    data object OnToggleTitleEditBoxVisibility: ProjectCardWithProfilesEvent()
    data class OnSaveProjectNameChange(val name : String) : ProjectCardWithProfilesEvent()
    //description edit field events
    data object OnRequestToOpenProjectDescriptionEditBox : ProjectCardWithProfilesEvent()
    data object OnToggleDescriptionEditBoxVisibility: ProjectCardWithProfilesEvent()
    data class OnSaveProjectDescriptionChange(val description : String) : ProjectCardWithProfilesEvent()
    //exit or delete project events
    data object OnRequestToExitOrDeleteProject : ProjectCardWithProfilesEvent()
    data object OnExitProject: ProjectCardWithProfilesEvent()
    data object OnDeleteProject : ProjectCardWithProfilesEvent()
    //initial data fetch event
    data class OnFetchUIData(val project : Project, val showProjectDetailInitially : Boolean, val currentUser : User) : ProjectCardWithProfilesEvent()
}