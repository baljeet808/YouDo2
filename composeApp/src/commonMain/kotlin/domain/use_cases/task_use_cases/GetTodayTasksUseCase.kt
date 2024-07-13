package domain.use_cases.task_use_cases

import data.local.entities.TaskEntity
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTodayTasksUseCase(
    private val repository: TaskRepository
){
    operator fun invoke(todayDateInLong: Long): Flow<List<TaskEntity>> {
        return repository.getTodayTasks(todayDateInLong)
    }
}