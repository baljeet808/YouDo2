package domain.use_cases.task_use_cases

import data.local.relations.TaskWithProject
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksWithProjectAsFlowUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(): Flow<List<TaskWithProject>> {
        return repository.getAllTasksWithProjectAsFlow()
    }
}