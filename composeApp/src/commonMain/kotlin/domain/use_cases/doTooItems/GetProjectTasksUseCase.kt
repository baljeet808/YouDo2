package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository

class GetProjectTasksUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke(projectId : String): List<TaskEntity> {
        return repository.getAllDoTooItemsByProjectID(projectId)
    }
}