package presentation.createproject.helpers

import domain.models.ColorPalette

sealed class CreateProjectScreenEvent {
    data class ProjectNameChanged(val name: String) : CreateProjectScreenEvent()
    data class ProjectDescriptionChanged(val description: String) : CreateProjectScreenEvent()
    data class ProjectColorChanged(val color: ColorPalette) : CreateProjectScreenEvent()
    data object ToggleDescriptionVisibility : CreateProjectScreenEvent()
    data object ToggleColorOptions : CreateProjectScreenEvent()
    data object CreateProject : CreateProjectScreenEvent()
    data object ToggleSuggestion : CreateProjectScreenEvent()
}