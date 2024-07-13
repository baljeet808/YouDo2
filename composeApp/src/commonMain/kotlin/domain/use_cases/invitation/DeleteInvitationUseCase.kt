package domain.use_cases.invitation

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository
class DeleteInvitationUseCase(
    private val repository: InvitationsRepository
){
    suspend operator fun invoke(invitation : InvitationEntity)  {
        return repository.delete(invitation)
    }
}