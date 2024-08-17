package presentation.complete_profile.helpers

import domain.dto_helpers.DataError


data class CompleteProfileUIState(
    val isLoading : Boolean = false,
    val enableSaveButton : Boolean = false,
    val error: DataError.Network? = null,
    val savedSuccessfully : Boolean = false,
    val selectedAvatar : String = "",
    val userName : String = ""
)
