package presentation.project.helpers

import domain.models.Project
import domain.models.Task


sealed class ProjectScreenEvent {
    data class ToggleTask(val task: Task) : ProjectScreenEvent()
    data class DeleteTask(val task: Task) : ProjectScreenEvent()
    data class UpdateProject(val project : Project) : ProjectScreenEvent()
    data class UpdateTask(val task : Task) : ProjectScreenEvent()
    data class DeleteProject(val project : Project) : ProjectScreenEvent()
    data object ToggleProjectDetail : ProjectScreenEvent()
}