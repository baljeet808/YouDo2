package presentation.login


data class LoginUIState(
    val isAuthenticating : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val showLoginForm : Boolean = false,
    val enableLoginButton : Boolean = false,
    val error: String? = null,
    val email : String = "",
    val password : String = "",
)
