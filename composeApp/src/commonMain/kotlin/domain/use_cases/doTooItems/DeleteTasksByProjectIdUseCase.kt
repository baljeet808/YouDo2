package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository

class DeleteTasksByProjectIdUseCase(
    private val repository: DoTooItemsRepository
){
    suspend operator fun invoke( projectId : String)  {
        return repository.deleteAllByProjectId( projectId)
    }
}