package presentation.signup.helpers

import domain.dto_helpers.DataError


data class SignupUIState(
    val isLoading : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val enableSignupButton : Boolean = false,
    val error: DataError.Network? = null,
    val signupSuccessful : Boolean = false,
)
