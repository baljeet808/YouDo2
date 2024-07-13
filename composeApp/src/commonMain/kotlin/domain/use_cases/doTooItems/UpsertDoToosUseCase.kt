package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository

class UpsertDoToosUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke(tasks : List<TaskEntity>)  {
        return repository.upsertAll(tasks)
    }
}