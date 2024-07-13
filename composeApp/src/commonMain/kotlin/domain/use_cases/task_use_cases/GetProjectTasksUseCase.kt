package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository

class GetProjectTasksUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(projectId : String): List<TaskEntity> {
        return repository.getAllTasksByProjectID(projectId)
    }
}