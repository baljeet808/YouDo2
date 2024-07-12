package data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "todos",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("projectId")]
)
data class TaskEntity(
    @PrimaryKey
    val id : String,
    val title : String,
    val projectId : String,
    val description : String,
    val dueDate : Long,
    val createDate : Long,
    var done : Boolean,
    val priority : String,
    var updatedBy : String
)