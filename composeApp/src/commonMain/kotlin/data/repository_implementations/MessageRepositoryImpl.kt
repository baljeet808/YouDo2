package data.repository_implementations

import data.local.entities.MessageEntity
import data.local.room.YouDo2Database
import domain.repository_interfaces.MessageRepository
import kotlinx.coroutines.flow.Flow


class MessageRepositoryImpl(
    localDB: YouDo2Database
) : MessageRepository {
    private val messageDao = localDB.messageDao()

    override suspend fun upsertAll(messages: List<MessageEntity>) {
        messageDao.upsertAll(messages)
    }

    override fun getAllMessagesAsFlow(): Flow<List<MessageEntity>> {
        return messageDao.getAllMessagesAsFlow()
    }

    override suspend fun getAllMessages(): List<MessageEntity> {
        return messageDao.getAllMessages()
    }

    override suspend fun getMessageById(messageId: String): MessageEntity? {
        return messageDao.getMessageById(messageId = messageId)
    }

    override fun getMessageByIdAsFlow(messageId: String): Flow<MessageEntity?> {
        return messageDao.getMessageByIdAsFlow(messageId)
    }

    override fun getAllMessagesOfAProjectAsFlow(projectId: String): Flow<List<MessageEntity>> {
        return messageDao.getAllMessagesOfAProjectAsFlow(projectId)
    }

    override suspend fun getAllMessagesOfAProject(projectId: String): List<MessageEntity> {
        return messageDao.getAllMessagesOfAProject(projectId)
    }

    override suspend fun delete(message: MessageEntity) {
        messageDao.delete(message)
    }

    override suspend fun deleteAllMessagesOfAProject(projectId: String) {
        messageDao.deleteAllMessagesOfAProject(projectId)
    }
}