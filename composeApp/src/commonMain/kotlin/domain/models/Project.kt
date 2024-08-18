package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Project(
    var id : String = "",
    var name : String = "",
    var description : String = "",
    var ownerId : String = "",
    var collaboratorIds : List<String> = emptyList(),
    var viewerIds: List<String> = emptyList(),
    var update : String = "",
    var color : String = "",
    var updatedAt : Long = 0L
)