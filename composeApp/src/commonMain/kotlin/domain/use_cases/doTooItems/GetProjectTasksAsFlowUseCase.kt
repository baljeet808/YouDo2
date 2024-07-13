package domain.use_cases.doTooItems

import data.local.entities.TaskEntity
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetProjectTasksAsFlowUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(projectId : String): Flow<List<TaskEntity>> {
        return repository.getAllDoTooItemsByProjectIDAsFlow(projectId)
    }
}