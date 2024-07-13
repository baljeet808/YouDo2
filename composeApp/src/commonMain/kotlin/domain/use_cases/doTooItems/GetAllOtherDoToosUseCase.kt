package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetAllOtherDoToosUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(tomorrowDateInLong : Long): Flow<List<TaskEntity>> {
        return repository.getAllOtherTasks(tomorrowDateInLong)
    }
}