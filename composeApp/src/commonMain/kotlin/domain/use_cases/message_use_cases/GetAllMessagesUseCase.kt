package domain.use_cases.message_use_cases

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository


class GetAllMessagesUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke() : List<MessageEntity> {
        return repository.getAllMessages()
    }
}