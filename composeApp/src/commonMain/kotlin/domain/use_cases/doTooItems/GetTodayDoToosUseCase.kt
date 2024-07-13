package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetTodayDoToosUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(todayDateInLong: Long): Flow<List<TaskEntity>> {
        return repository.getTodayTasks(todayDateInLong)
    }
}