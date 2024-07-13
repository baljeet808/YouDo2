package domain.use_cases.task_use_cases

import data.local.relations.TaskWithProject
import domain.repository_interfaces.TaskRepository

class GetAllTasksWithProjectUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke(): List<TaskWithProject> {
        return repository.getAllTasksWithProject()
    }
}