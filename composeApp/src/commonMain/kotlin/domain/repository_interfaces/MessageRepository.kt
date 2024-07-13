package domain.repository_interfaces

import data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow


interface MessageRepository {
     suspend fun upsertAll(messages : List<MessageEntity>)

     // fun getAllMessageByProjectId(projectId: String): PagingSource<Int, MessageEntity>

     fun getAllMessagesAsFlow() : Flow<List<MessageEntity>>
     suspend fun getAllMessages() : List<MessageEntity>
     suspend fun getMessageById(messageId: String) : MessageEntity?
     fun getMessageByIdAsFlow(messageId: String) : Flow<MessageEntity?>
     fun getAllMessagesOfAProjectAsFlow(projectId: String) : Flow<List<MessageEntity>>
     suspend fun getAllMessagesOfAProject(projectId: String) : List<MessageEntity>
     suspend fun delete(message: MessageEntity)
     suspend fun deleteAllMessagesOfAProject(projectId: String)
}