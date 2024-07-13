package domain.use_cases.project

import data.local.relations.ProjectWithDoToos
import domain.repository_interfaces.ProjectRepository
import kotlinx.coroutines.flow.Flow


class GetProjectsWithDoToosUseCase(
    private val repository: ProjectRepository
){
    operator fun invoke(): Flow<List<ProjectWithDoToos>> {
        return repository.getAllProjectsAndTasksAsFlow()
    }
}