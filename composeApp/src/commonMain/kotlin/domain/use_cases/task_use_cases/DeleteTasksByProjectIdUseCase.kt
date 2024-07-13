package domain.use_cases.task_use_cases

import domain.repository_interfaces.TaskRepository

class DeleteTasksByProjectIdUseCase(
    private val repository: TaskRepository
){
    suspend operator fun invoke( projectId : String)  {
        return repository.deleteAllByProjectId( projectId)
    }
}