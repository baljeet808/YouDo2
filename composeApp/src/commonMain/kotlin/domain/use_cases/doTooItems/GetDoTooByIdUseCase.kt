package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository

class GetDoTooByIdUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke(doTooId : String): TaskEntity {
        return repository.getDoTooById(doTooId)
    }
}