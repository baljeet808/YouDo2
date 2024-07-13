package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository

class UpsertTasksUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(tasks : List<TaskEntity>)  {
        return repository.upsertAll(tasks)
    }
}