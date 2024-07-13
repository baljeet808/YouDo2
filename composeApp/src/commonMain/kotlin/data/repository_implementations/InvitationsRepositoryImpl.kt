package data.repository_implementations

import data.local.entities.InvitationEntity
import data.local.room.YouDo2Database
import domain.repository_interfaces.InvitationsRepository
import kotlinx.coroutines.flow.Flow


class InvitationsRepositoryImpl (
    localDB: YouDo2Database
) : InvitationsRepository {

    private val invitationDao = localDB.invitationDao()

    override fun getAllInvitations(): Flow<List<InvitationEntity>> {
        return invitationDao.getAllInvitations()
    }

    override fun getAllInvitationsByProjectID(projectId: String): Flow<List<InvitationEntity>> {
        return invitationDao.getAllInvitationsByProjectID(projectId)
    }
    override fun getInvitationByIdAsFlow(id: String): Flow<InvitationEntity?> {
        return invitationDao.getInvitationByIdAsFlow(id)
    }

    override suspend fun getInvitationById(id: String): InvitationEntity? {
        return invitationDao.getInvitationById(id)
    }

    override suspend fun upsertAll(invitations: List<InvitationEntity>) {
        return invitationDao.upsertAll(invitations)
    }

    override suspend fun delete(invitation: InvitationEntity) {
        return invitationDao.delete(invitation)
    }

    override suspend fun deleteAllByProjectId(projectId: String) {
        return invitationDao.deleteAllByProjectId(projectId)
    }
}