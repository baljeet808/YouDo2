package domain.use_cases.invitation

import data.local.entities.InvitationEntity
import domain.repository_interfaces.InvitationsRepository
import kotlinx.coroutines.flow.Flow

class GetAllInvitationsByProjectIdUseCase(
    private val repository: InvitationsRepository
){
    operator fun invoke( projectId : String ): Flow<List<InvitationEntity>> {
        return repository.getAllInvitationsByProjectID(projectId)
    }
}