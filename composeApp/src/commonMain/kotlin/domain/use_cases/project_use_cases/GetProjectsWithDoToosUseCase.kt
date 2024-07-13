package domain.use_cases.project_use_cases

import data.local.relations.ProjectWithTasks
import domain.repository_interfaces.ProjectRepository
import kotlinx.coroutines.flow.Flow


class GetProjectsWithDoToosUseCase(
    private val repository: ProjectRepository
){
    operator fun invoke(): Flow<List<ProjectWithTasks>> {
        return repository.getAllProjectsAndTasksAsFlow()
    }
}