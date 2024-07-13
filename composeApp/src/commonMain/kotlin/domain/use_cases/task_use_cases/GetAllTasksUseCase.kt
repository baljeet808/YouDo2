package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(): Flow<List<TaskEntity>> {
        return repository.getAllTasks()
    }
}