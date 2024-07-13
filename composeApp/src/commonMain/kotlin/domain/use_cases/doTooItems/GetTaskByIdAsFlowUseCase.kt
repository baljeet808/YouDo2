package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetTaskByIdAsFlowUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(taskId : String): Flow<TaskEntity> {
        return repository.getTaskByIdAsAFlow(taskId)
    }
}