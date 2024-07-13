package domain.use_cases.messages

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetAllMessagesByProjectIDAsFlowUseCase(
    private val repository: MessageRepository
){
    operator fun invoke(projectId : String) : Flow<List<MessageEntity>> {
        return repository.getAllMessagesOfAProjectAsFlow(projectId)
    }
}