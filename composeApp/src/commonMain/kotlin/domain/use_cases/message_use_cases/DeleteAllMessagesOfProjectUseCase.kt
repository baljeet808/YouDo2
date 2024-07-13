package domain.use_cases.message_use_cases

import domain.repository_interfaces.MessageRepository


class DeleteAllMessagesOfProjectUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke(projectId: String) {
        return repository.deleteAllMessagesOfAProject(projectId)
    }
}