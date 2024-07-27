package presentation.login

sealed class LoginScreenEvents {
    data class OnAttemptToLogin(val email : String, val password : String) : LoginScreenEvents()
    data class OnPasswordChange(val password : String) : LoginScreenEvents()
    data class OnEmailChange(val email : String) : LoginScreenEvents()
    data class OnOnboardingPageNumberChanged(val pageNumber : Int): LoginScreenEvents()
    data object OnRefreshUIState : LoginScreenEvents()
}