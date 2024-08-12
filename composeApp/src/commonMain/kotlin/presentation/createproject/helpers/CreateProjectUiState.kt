package presentation.createproject.helpers

import common.EnumProjectColors
import common.getRandomColorEnum

data class CreateProjectUiState (
    val projectName : String = "",
    val projectDescription : String = "",
    val projectColor : EnumProjectColors =  getRandomColorEnum(),
    val showColorOptions : Boolean = false,
    val showDescription : Boolean = false,
    val userId : String = "",
    val userName : String = "",
    val userEmail : String = "",
    val showTitleErrorAnimation : Boolean = false,
    val showDescriptionErrorAnimation : Boolean = false,
    val showKeyboard : Boolean = true,
    val focusOnTitle : Boolean = true,
    val focusOnDescription : Boolean = false
)