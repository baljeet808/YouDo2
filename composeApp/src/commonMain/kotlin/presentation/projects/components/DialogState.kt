package presentation.projects.components

import data.local.relations.TaskWithProject

sealed class DialogState {
    data object None : DialogState()
    data object ViewerPermission : DialogState()
    data class DeleteConfirmation(val task: TaskWithProject) : DialogState()
}