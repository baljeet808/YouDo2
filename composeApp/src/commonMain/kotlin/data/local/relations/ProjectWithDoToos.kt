package data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import data.local.entities.ProjectEntity
import data.local.entities.TaskEntity

data class ProjectWithDoToos(

    @Embedded
    val project : ProjectEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "projectId",
        entity = TaskEntity::class
    )
    var tasks : List<TaskEntity> = listOf()

)
