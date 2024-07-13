package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetProjectTasksAsFlowUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(projectId : String): Flow<List<TaskEntity>> {
        return repository.getAllTasksByProjectIDAsFlow(projectId)
    }
}