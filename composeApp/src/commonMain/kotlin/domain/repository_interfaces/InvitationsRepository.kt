package domain.repository_interfaces

import data.local.entities.InvitationEntity
import kotlinx.coroutines.flow.Flow

interface InvitationsRepository {

     suspend fun upsertAll(invitations : List<InvitationEntity>)

     fun getAllInvitationsByProjectID(projectId : String): Flow<List<InvitationEntity>>

     fun getAllInvitations(): Flow<List<InvitationEntity>>

     suspend fun getInvitationById(id : String): InvitationEntity?

     fun getInvitationByIdAsFlow(id : String): Flow<InvitationEntity?>

     suspend fun delete(invitation : InvitationEntity)

     suspend fun deleteAllByProjectId(projectId: String)

}