package data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "messages",
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
data class MessageEntity(
    @PrimaryKey
    var id: String,
    var senderId: String,
    var message: String,
    var createdAt: Long,
    var isUpdate: Boolean,
    var attachmentUrl: String?,
    var attachmentName: String?,
    var interactions: String,
    var projectId: String
)