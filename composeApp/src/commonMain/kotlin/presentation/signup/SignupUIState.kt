package presentation.signup


data class SignupUIState(
    val isAuthenticating : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val enableSignupButton : Boolean = false,
    val error: String? = null,
    val signupSuccessful : Boolean = false,
)
