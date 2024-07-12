package data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import data.local.entities.ProjectEntity
import data.local.entities.TaskEntity

data class TaskWithProject(

    @Embedded
    val task : TaskEntity,

    @Relation(
        parentColumn = "projectId",
        entityColumn = "id",
        entity = ProjectEntity::class
    )
    var projectEntity : ProjectEntity

)
