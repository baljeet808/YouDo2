package domain.use_cases.project

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository


class DeleteProjectUseCase(
    private val repository: ProjectRepository
){
    suspend operator fun invoke(project : ProjectEntity)  {
        return repository.delete(project)
    }
}