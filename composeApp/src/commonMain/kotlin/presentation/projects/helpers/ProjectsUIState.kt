package presentation.projects.helpers

import data.local.relations.ProjectWithTasks
import domain.dto_helpers.DataError


data class ProjectsUIState(
    val isLoading : Boolean = false,
    val error: DataError.Network? = null,
    val projects : List<ProjectWithTasks> = emptyList(),
    val userName : String = "",
    val userId : String = "",
    val userEmail : String = "",
)
