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
    var color : String = "Pink",
    var updatedAt : Long = 0L,
    var category : String = "",
    var photoUrl : String = "",
    var extraUrl : String = "",
    var projectLikeIds : List<String> = emptyList(),
    var numberOfTasks : Int = 0,
)