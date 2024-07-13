package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository


class DeleteDoTooUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke(task : TaskEntity)  {
        return repository.delete(task)
    }
}