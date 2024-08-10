package presentation.signup

import presentation.login.LoginScreenEvents

sealed class SignupScreenEvents {
    data class OnAttemptToSignup(val email : String, val password : String) : SignupScreenEvents()
    data class OnPasswordChange(val password : String) : SignupScreenEvents()
    data class OnEmailChange(val email : String) : SignupScreenEvents()
    data object OnRefreshUIState : SignupScreenEvents()
}