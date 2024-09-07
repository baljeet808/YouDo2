package presentation.create_task.helpers


sealed class CreateTaskScreenEvent {
    data class TaskNameChanged(val name: String) : CreateTaskScreenEvent()
    data class TaskDescriptionChanged(val description: String) : CreateTaskScreenEvent()
    data object ToggleDescriptionVisibility : CreateTaskScreenEvent()
    data object CreateTask : CreateTaskScreenEvent()
}