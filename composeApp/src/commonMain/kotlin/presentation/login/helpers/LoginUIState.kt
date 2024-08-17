package presentation.login.helpers

import common.getRandomLoginHeading
import common.getRandomPasswordPlaceholder
import domain.dto_helpers.DataError


data class LoginUIState(
    val isLoading : Boolean = false,
    val passwordInValid : Boolean = false,
    val emailInValid : Boolean = false,
    val enableLoginButton : Boolean = false,
    val error: DataError.Network? = null,
    val loginSuccessful : Boolean = false,
    val heading : String = getRandomLoginHeading(),
    val passwordPlaceholder : String = getRandomPasswordPlaceholder(),
    val email : String = "",
    val password : String = ""
)
