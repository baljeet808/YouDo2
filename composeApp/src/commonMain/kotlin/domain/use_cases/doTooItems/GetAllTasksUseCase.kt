package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(): Flow<List<TaskEntity>> {
        return repository.getAllDoTooItems()
    }
}