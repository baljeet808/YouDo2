package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetPendingDoToosUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(yesterdayDateInLong : Long): Flow<List<TaskEntity>> {
        return repository.getPendingTasks(yesterdayDateInLong)
    }
}