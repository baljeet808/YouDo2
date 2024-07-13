package domain.use_cases.project_use_cases

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository
import kotlinx.coroutines.flow.Flow


class GetProjectByIdAsFlowUseCase(
    private val repository: ProjectRepository
){
    operator fun invoke(projectId : String): Flow<ProjectEntity> {
        return repository.getProjectByIdAsFlow(projectId)
    }
}