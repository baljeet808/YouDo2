package presentation.createproject.helpers

import common.COLOR_GRAPHITE_VALUE
import domain.dto_helpers.DataError

data class CreateProjectUiState (
    val projectName : String = "",
    val projectDescription : String = "",
    val projectColor : Long =  COLOR_GRAPHITE_VALUE,
    val selectedColorName : String = "",
    val showColorOptions : Boolean = false,
    val showDescription : Boolean = false,
    val showPreview : Boolean = false,
    val showSuggestion : Boolean = false,
    val userId : String = "",
    val userName : String = "",
    val userEmail : String = "",
    val userAvatarUrl : String = "",
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