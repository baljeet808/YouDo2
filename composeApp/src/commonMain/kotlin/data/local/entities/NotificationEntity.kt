package data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import common.EnumNotificationType

@Entity(
    tableName = "notifications"
)
data class NotificationEntity(
    @PrimaryKey
    val id : String,
    val title : String,
    val contentText : String,
    val invitationId : String?,
    val projectId : String?,
    val taskId : String?,
    val messageId : String?,
    val createdAt : Long,
    var projectColor : String,
    val isNew : Boolean,
    val notificationType : EnumNotificationType
)