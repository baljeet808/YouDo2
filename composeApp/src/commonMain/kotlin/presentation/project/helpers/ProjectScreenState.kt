package presentation.project.helpers

import common.EnumRoles
import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.Task
import domain.models.User

data class ProjectScreenState(
    val isLoading : Boolean = false,
    val error: DataError.Network? = null,
    val userName : String = "",
    val userId : String = "",
    val userEmail : String = "",
    val userAvatarUrl : String = "",
    val project : Project = Project(),
    val tasks : List<Task> = emptyList(),
    val users : List<User> = emptyList(),
    val role : EnumRoles = EnumRoles.Viewer,
    val showProjectDetail : Boolean = true,
    val projectDeleted : Boolean = false
)
