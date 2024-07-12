package data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import data.local.entities.InvitationEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface InvitationDao {

    @Upsert
    suspend fun upsertAll(invitations : List<InvitationEntity>)

    @Query("SELECT * FROM invitations WHERE projectId = :projectId")
    fun getAllInvitationsByProjectID(projectId: String) : Flow<List<InvitationEntity>>

    @Query("SELECT * FROM invitations")
    fun getAllInvitations() : Flow<List<InvitationEntity>>

    @Query("SELECT * FROM invitations where id = :id")
    suspend fun getInvitationById(id : String) : InvitationEntity?

    @Query("SELECT * FROM invitations where id = :id")
    fun getInvitationByIdAsFlow(id : String) : Flow<InvitationEntity?>

    @Delete
    suspend fun delete(invitation : InvitationEntity)

    @Query("DELETE FROM invitations where projectId = :projectId")
    suspend fun deleteAllByProjectId(projectId : String)
}