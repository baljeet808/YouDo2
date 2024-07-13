package domain.use_cases.invitation

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository
import kotlinx.coroutines.flow.Flow

class GetInvitationByIdAsFlowUseCase(
    private val repository: InvitationsRepository
){
    operator fun invoke(id : String): Flow<InvitationEntity?> {
        return repository.getInvitationByIdAsFlow(id)
    }
}