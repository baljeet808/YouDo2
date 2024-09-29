package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class Task(
    var createDate : Long = 0L,
    var description : String = "",
    var done : Boolean = false,
    var dueDate : Long = 0L,
    var id : String = "",
    var priority : String = "High",
    var projectId : String = "",
    var title : String = "",
    var updatedBy : String = ""
)