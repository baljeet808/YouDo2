package presentation.login.helpers


data class LoginUIState(
    val isLoading : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val showLoginForm : Boolean = false,
    val enableLoginButton : Boolean = false,
    val error: String? = null,
    val loginSuccessful : Boolean = false,
)
