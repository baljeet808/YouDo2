package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Task(
    var id : String = "",
    var title : String = "",
    var projectId : String = "",
    var description : String = "",
    var dueDate : Long = 0L,
    var createDate : Long = 0L,
    var done : Boolean = false,
    var priority : String = "High",
    var updatedBy : String = ""
)