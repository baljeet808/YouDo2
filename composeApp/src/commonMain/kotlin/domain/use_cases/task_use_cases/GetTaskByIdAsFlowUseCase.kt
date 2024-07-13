package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskByIdAsFlowUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(taskId : String): Flow<TaskEntity> {
        return repository.getTaskByIdAsAFlow(taskId)
    }
}