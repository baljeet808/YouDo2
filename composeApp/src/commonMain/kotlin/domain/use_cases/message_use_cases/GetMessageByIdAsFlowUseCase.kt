package domain.use_cases.message_use_cases

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessageByIdAsFlowUseCase(
    private val repository: MessageRepository
){
    operator fun invoke(messageId : String) : Flow<MessageEntity?> {
        return repository.getMessageByIdAsFlow(messageId)
    }
}