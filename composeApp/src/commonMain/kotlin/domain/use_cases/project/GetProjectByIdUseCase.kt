package domain.use_cases.project

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository

class GetProjectByIdUseCase (
    private val repository: ProjectRepository
){
    suspend operator fun invoke(projectId : String): ProjectEntity {
        return repository.getProjectById(projectId)
    }
}