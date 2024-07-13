package domain.use_cases.doTooItems

import data.local.relations.TaskWithProject
import domain.repository_interfaces.DoTooItemsRepository

class GetAllTasksWithProjectUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke(): List<TaskWithProject> {
        return repository.getAllTasksWithProject()
    }
}