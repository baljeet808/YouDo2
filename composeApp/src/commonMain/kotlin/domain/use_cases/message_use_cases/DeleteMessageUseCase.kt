package domain.use_cases.message_use_cases

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository

class DeleteMessageUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke(message : MessageEntity) {
        return repository.delete(message)
    }
}