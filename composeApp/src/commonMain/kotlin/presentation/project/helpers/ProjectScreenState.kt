package presentation.project.helpers

import common.EnumRoles
import data.local.entities.ProjectEntity
import data.local.entities.TaskEntity
import data.local.entities.UserEntity
import domain.dto_helpers.DataError

data class ProjectScreenState(
    val isLoading : Boolean = false,
    val error: DataError.Network? = null,
    val userName : String = "",
    val userId : String = "",
    val userEmail : String = "",
    val userAvatarUrl : String = "",
    val project : ProjectEntity = ProjectEntity(),
    val tasks : List<TaskEntity> = emptyList(),
    val users : List<UserEntity> = emptyList(),
    val role : EnumRoles = EnumRoles.Viewer
)
