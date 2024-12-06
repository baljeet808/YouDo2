package presentation.create_task.helpers

import androidx.compose.ui.graphics.Color
import common.DueDates
import common.EnumPriorities
import domain.dto_helpers.DataError
import domain.models.Project

data class CreateTaskUiState(
    val taskName: String = "",
    val taskDescription: String = "",
    val priority: EnumPriorities = EnumPriorities.LOW,
    val dueDate: DueDates = DueDates.CUSTOM,
    val project: Project? = null,
    val projectColor: Color =  Color(0xff3F292B),
    val showDescription: Boolean = false,
    val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val showTitleErrorAnimation: Boolean = false,
    val showDescriptionErrorAnimation: Boolean = false,
    val showKeyboard: Boolean = true,
    val focusOnTitle: Boolean = true,
    val focusOnDescription: Boolean = false,
    val isLoading: Boolean = false,
    val enableSaveButton: Boolean = false,
    val success: Boolean = false,
    val error: DataError? = null,
)