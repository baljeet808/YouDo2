package presentation.dashboard.helpers

import domain.dto_helpers.DataError
import domain.models.Project
import domain.models.User


data class DashboardUIState(
    val isLoading : Boolean = false,
    val error: DataError.Network? = null,
    val userName : String = "",
    val userId : String = "",
    val userEmail : String = "",
    val userAvatarUrl : String = "",
    val isLoggedOut : Boolean = false,
    val projects : List<Project> = emptyList(),
    val currentUser : User = User()
)
