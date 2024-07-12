package data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import data.local.entities.MessageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MessageDao {
    @Upsert
    suspend fun upsertAll(messages : List<MessageEntity>)
/*
    @Query("SELECT * FROM messages WHERE projectId = :projectId ORDER BY createdAt DESC")
    suspend fun getAllMessageByProjectId(projectId: String) : PagingSource<Int, MessageEntity>*/

    @Query("SELECT * FROM messages")
    fun getAllMessagesAsFlow() : Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages() : List<MessageEntity>

    @Query("SELECT * FROM messages where id = :messageId ")
    suspend fun getMessageById(messageId: String) : MessageEntity?

    @Query("SELECT * FROM messages where id = :messageId ")
    fun getMessageByIdAsFlow(messageId: String) : Flow<MessageEntity?>

    @Query("SELECT * FROM messages WHERE projectId = :projectId")
    fun getAllMessagesOfAProjectAsFlow(projectId: String) : Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE projectId = :projectId")
    suspend fun getAllMessagesOfAProject(projectId: String) : List<MessageEntity>

    @Delete
    suspend fun delete(message: MessageEntity)

    @Query("DELETE FROM messages where projectId = :projectId")
    suspend fun deleteAllMessagesOfAProject(projectId: String)

}