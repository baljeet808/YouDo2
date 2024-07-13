package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository

class GetTaskByIdUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(doTooId : String): TaskEntity {
        return repository.getTaskById(doTooId)
    }
}