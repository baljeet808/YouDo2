package domain.use_cases.invitation_use_cases

import domain.repository_interfaces.InvitationsRepository
class DeleteAllInvitationsByProjectIdUseCase(
    private val repository: InvitationsRepository
){
    suspend operator fun invoke(projectId : String)  {
        return repository.deleteAllByProjectId(projectId)
    }
}