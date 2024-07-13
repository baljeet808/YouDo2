package domain.use_cases.doTooItems

import data.local.relations.TaskWithProject
import domain.repository_interfaces.DoTooItemsRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasksWithProjectAsFlowUseCase(
    private val repository: DoTooItemsRepository
){
    operator fun invoke(): Flow<List<TaskWithProject>> {
        return repository.getAllTasksWithProjectAsFlow()
    }
}