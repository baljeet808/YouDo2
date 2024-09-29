package data.local.mappers

import data.local.entities.MessageEntity
import domain.models.Message


fun MessageEntity.toMessage() : Message{
    return Message(
        id = id,
        senderId = senderId,
        message = message,
        createdAt = createdAt,
        isUpdate = isUpdate,
        attachmentUrl = attachmentUrl,
        attachmentName = attachmentName,
        interactions = interactions,
        projectId = projectId
    )
}

fun Message.toMessageEntity() : MessageEntity{
    return MessageEntity(
        id = id,
        senderId = senderId,
        message = message,
        createdAt = createdAt,
        isUpdate = isUpdate,
        attachmentUrl = attachmentUrl,
        attachmentName = attachmentName,
        interactions = interactions,
        projectId = projectId
    )
}