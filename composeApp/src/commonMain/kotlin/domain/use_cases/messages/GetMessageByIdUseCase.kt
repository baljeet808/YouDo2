package domain.use_cases.messages

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository

class GetMessageByIdUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke(messageId : String) : MessageEntity? {
        return repository.getMessageById(messageId)
    }
}