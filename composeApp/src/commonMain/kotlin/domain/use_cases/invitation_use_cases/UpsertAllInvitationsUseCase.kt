package domain.use_cases.invitation_use_cases

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository

class UpsertAllInvitationsUseCase(
    private val repository: InvitationsRepository
){
    suspend operator fun invoke(invitations : List<InvitationEntity>)  {
        return repository.upsertAll(invitations)
    }
}