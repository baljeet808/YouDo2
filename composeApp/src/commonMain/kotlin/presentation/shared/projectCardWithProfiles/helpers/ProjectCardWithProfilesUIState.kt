package presentation.shared.projectCardWithProfiles.helpers

import common.EnumRoles
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.User

data class ProjectCardWithProfilesUIState(
    //api states
    val isLoadingProject : Boolean = false,
    val isLoadingUsers : Boolean = false,
    val error : DataError.Network? = null,
    //data
    val projectAdmin : User = User(),
    val project : Project? = Project(),
    val usersInProject : List<User> = emptyList(),
    val userRoleInProject : EnumRoles = EnumRoles.Viewer,
    val currentUser : User = User(),
    //ui booleans
    val showProjectDetail : Boolean = true,
    val showEditTitleBox : Boolean = false,
    val showEditDescriptionBox : Boolean = false,
    //dialogs
    val showViewerPermissionDialog : Boolean = false,
    val showConfirmProjectDeletionDialog : Boolean = false,
    val showConfirmExitProjectDialog : Boolean = false

)

