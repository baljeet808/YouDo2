package domain.repository_interfaces

import data.local.entities.ProjectEntity
import data.local.relations.ProjectWithDoToos
import kotlinx.coroutines.flow.Flow


interface ProjectRepository {
     suspend fun upsertAll(projects : List<ProjectEntity>)
     fun getAllProjectsAsFlow(): Flow<List<ProjectEntity>>
     suspend fun getAllProjects(): List<ProjectEntity>
     suspend fun getProjectById(projectId : String): ProjectEntity
     fun getProjectByIdAsFlow(projectId : String): Flow<ProjectEntity>
     fun getAllProjectsAndTasksAsFlow(): Flow<List<ProjectWithDoToos>>
     suspend fun delete(project : ProjectEntity)
     suspend fun search(searchQuery : String) : List<ProjectEntity>
}