package domain.use_cases.project_use_cases

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository


class GetProjectsUseCase (
    private val repository: ProjectRepository
){
    suspend operator fun invoke(): List<ProjectEntity> {
        return repository.getAllProjects()
    }
}