package presentation.dashboard


data class DashboardUIState(
    val isLoggingOut : Boolean = false,
    val error: String? = null,
    val userName : String = "",
    val userId : String = "",
    val userEmail : String = "",
    val userAvatarUrl : String = "",
    val isLoggedOut : Boolean = false
)
