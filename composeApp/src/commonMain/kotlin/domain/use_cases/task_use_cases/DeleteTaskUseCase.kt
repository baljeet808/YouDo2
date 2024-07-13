package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository


class DeleteTaskUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(task : TaskEntity)  {
        return repository.delete(task)
    }
}