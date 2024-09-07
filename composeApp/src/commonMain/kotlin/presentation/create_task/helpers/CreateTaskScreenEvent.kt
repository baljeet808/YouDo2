package presentation.create_task.helpers

import common.DueDates
import common.EnumPriorities


sealed class CreateTaskScreenEvent {
    data class TaskNameChanged(val name: String) : CreateTaskScreenEvent()
    data class TaskDescriptionChanged(val description: String) : CreateTaskScreenEvent()
    data object ToggleDescriptionVisibility : CreateTaskScreenEvent()
    data class TaskPriorityChanged( val priority: EnumPriorities) : CreateTaskScreenEvent()
    data class TaskDueDateChanged(val dueDate: DueDates) : CreateTaskScreenEvent()
    data object CreateTask : CreateTaskScreenEvent()
}