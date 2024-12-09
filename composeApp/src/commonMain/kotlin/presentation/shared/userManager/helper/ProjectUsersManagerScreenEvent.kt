package presentation.shared.userManager.helper

import common.EnumRoles
import domain.models.User

sealed class ProjectUsersManagerScreenEvent {
    data object OnClickedAddNewButton: ProjectUsersManagerScreenEvent()
    data class OnClickedSubmitCodeButton(val sharingCode : String) : ProjectUsersManagerScreenEvent()
    data object OnClickedDismissAddNewBox : ProjectUsersManagerScreenEvent()
    data class OnSharingCodeUpdated(val sharingCode : String) : ProjectUsersManagerScreenEvent()
    data class OnRecentUserCheckChanged(val isChecked : Boolean, val user : User) : ProjectUsersManagerScreenEvent()
    data class OnUserRolesChanged(val user : User, val role : EnumRoles) : ProjectUsersManagerScreenEvent()
}