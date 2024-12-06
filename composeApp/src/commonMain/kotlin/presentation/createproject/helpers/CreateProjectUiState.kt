package presentation.createproject.helpers

import domain.dto_helpers.DataError

data class CreateProjectUiState (
    val projectName : String = "",
    val projectDescription : String = "",
    val projectColor : Long =  0,
    val selectedColorName : String = "",
    val showColorOptions : Boolean = false,
    val showDescription : Boolean = false,
    val showSuggestion : Boolean = false,
    val userId : String = "",
    val userName : String = "",
    val userEmail : String = "",
    val showTitleErrorAnimation : Boolean = false,
    val showDescriptionErrorAnimation : Boolean = false,
    val showKeyboard : Boolean = true,
    val focusOnTitle : Boolean = true,
    val focusOnDescription : Boolean = false,
    val isLoading : Boolean = false,
    val enableSaveButton : Boolean = false,
    val success : Boolean = false,
    val error : DataError? = null,
)