package domain.use_cases.invitation

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository
import kotlinx.coroutines.flow.Flow

class GetAllInvitationsUseCase(
    private val repository: InvitationsRepository
){
    operator fun invoke(): Flow<List<InvitationEntity>> {
        return repository.getAllInvitations()
    }
}