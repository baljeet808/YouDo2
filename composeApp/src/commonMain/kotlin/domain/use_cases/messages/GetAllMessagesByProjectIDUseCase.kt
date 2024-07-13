package domain.use_cases.messages

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository
class GetAllMessagesByProjectIDUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke(projectId : String) : List<MessageEntity> {
        return repository.getAllMessagesOfAProject(projectId)
    }
}