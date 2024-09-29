package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
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