package presentation.createproject.helpers

import common.EnumProjectColors

sealed class CreateProjectScreenEvent {
    data class ProjectNameChanged(val name: String) : CreateProjectScreenEvent()
    data class ProjectDescriptionChanged(val description: String) : CreateProjectScreenEvent()
    data class ProjectColorChanged(val color: EnumProjectColors) : CreateProjectScreenEvent()
    data object ToggleDescriptionVisibility : CreateProjectScreenEvent()
    data object ToggleColorOptions : CreateProjectScreenEvent()
    data object CreateProject : CreateProjectScreenEvent()
    data object ToggleSuggestion : CreateProjectScreenEvent()
}