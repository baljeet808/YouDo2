package presentation.login.helpers

import domain.dto_helpers.DataError


data class LoginUIState(
    val isLoading : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val enableLoginButton : Boolean = false,
    val error: DataError.Network? = null,
    val loginSuccessful : Boolean = false,
)
