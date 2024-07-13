package domain.use_cases.message_use_cases

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetAllMessagesAsFlowUseCase(
    private val repository: MessageRepository
){
    operator fun invoke() : Flow<List<MessageEntity>> {
        return repository.getAllMessagesAsFlow()
    }
}