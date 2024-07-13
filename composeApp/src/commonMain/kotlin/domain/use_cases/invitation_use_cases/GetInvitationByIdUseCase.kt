package domain.use_cases.invitation_use_cases

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository

class GetInvitationByIdUseCase(
    private val repository: InvitationsRepository
){
    suspend operator fun invoke(id : String): InvitationEntity? {
        return repository.getInvitationById(id)
    }
}