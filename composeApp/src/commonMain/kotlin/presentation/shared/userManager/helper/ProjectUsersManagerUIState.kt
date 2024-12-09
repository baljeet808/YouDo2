package presentation.shared.userManager.helper

import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.User

data class ProjectUsersManagerUIState(
    val isLoading : Boolean = false,
    val project : Project = Project(),
    val error : DataError.Network? = null,
    val collaborators : List<User> = emptyList(),
    val projectUsers : List<User> = emptyList(),
    val newUserSharingCode : String = "",
    val showRecent : Boolean = true,
    val showAddNewBox : Boolean = false,
    val showSharingCodeError : Boolean = false,
)
