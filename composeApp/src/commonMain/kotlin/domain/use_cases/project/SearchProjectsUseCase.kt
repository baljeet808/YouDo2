package domain.use_cases.project

import data.local.entities.ProjectEntity
import domain.repository_interfaces.ProjectRepository


class SearchProjectsUseCase (
    private val repository: ProjectRepository
){
    suspend fun invoke(searchQuery: String ) :List<ProjectEntity> {
        return repository.search(searchQuery)
    }
}