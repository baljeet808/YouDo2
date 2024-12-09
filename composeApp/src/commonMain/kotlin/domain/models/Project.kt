package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Project(
    var id : String = "",
    var name : String = "",
    var description : String = "",
    var ownerId : String = "",
    var ownerName : String = "",
    var ownerAvatarUrl : String = "",
    var collaboratorIds : List<String> = emptyList(),
    var viewerIds: List<String> = emptyList(),
    var update : String = "",
    var color : Long = 0L,
    var updatedAt : Long = 0L,
    var category : String = "",
    var photoUrl : String = "",
    var extraUrl : String = "",
    var projectLikeIds : List<String> = emptyList(),
    var numberOfTasks : Int = 0,
)


fun Project.getAllIds () : List<String> {
    return collaboratorIds + viewerIds + ownerId
}

fun Project.getAllCollaboratorsIds () : List<String> {
    return collaboratorIds + viewerIds
}