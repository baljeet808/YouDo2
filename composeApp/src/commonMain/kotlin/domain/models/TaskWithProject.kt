package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskWithProject(
    val task : Task,
    var project : Project
)
