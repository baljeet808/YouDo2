package domain.use_cases.project

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository


class UpsertProjectUseCase (
    private val repository: ProjectRepository
){
    suspend operator fun invoke(projects: List<ProjectEntity> ) {
        return repository.upsertAll(projects)
    }
}