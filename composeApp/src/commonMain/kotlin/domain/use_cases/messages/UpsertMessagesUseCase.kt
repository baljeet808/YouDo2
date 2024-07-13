package domain.use_cases.messages

import data.local.entities.MessageEntity
import domain.repository_interfaces.MessageRepository

class UpsertMessagesUseCase(
    private val repository: MessageRepository
){
    suspend operator fun invoke(messages : List<MessageEntity>) {
        repository.upsertAll(messages)
    }
}