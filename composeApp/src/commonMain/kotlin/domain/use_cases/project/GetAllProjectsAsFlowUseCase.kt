package domain.use_cases.project

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository
import kotlinx.coroutines.flow.Flow

class GetAllProjectsAsFlowUseCase(
    private val repository: ProjectRepository
){
    operator fun invoke(): Flow<List<ProjectEntity>> {
        return repository.getAllProjectsAsFlow()
    }
}