package data.repository_implementations

import data.local.entities.ProjectEntity
import data.local.relations.ProjectWithTasks
import data.local.room.YouDo2Database
import domain.repository_interfaces.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectRepositoryImpl(localDB: YouDo2Database) : ProjectRepository {

    private val projectDao = localDB.projectDao()
    override suspend fun upsertAll(projects: List<ProjectEntity>) {
        return projectDao.upsertAll(projects)
    }

    override fun getAllProjectsAsFlow(): Flow<List<ProjectEntity>> {
        return projectDao.getAllProjectsAsFlow()
    }

    override suspend fun getAllProjects(): List<ProjectEntity> {
        return projectDao.getAllProjects()
    }

    override suspend fun getProjectById(projectId: String): ProjectEntity {
        return projectDao.getProjectById(projectId)
    }

    override fun getProjectByIdAsFlow(projectId: String): Flow<ProjectEntity> {
        return projectDao.getProjectByIdAsFlow(projectId)
    }

    override fun getAllProjectsAndTasksAsFlow(): Flow<List<ProjectWithTasks>> {
        return projectDao.getAllProjectsAndTasksAsFlow()
    }

    override suspend fun delete(project: ProjectEntity) {
        return projectDao.delete(project)
    }

    override suspend fun search(searchQuery: String): List<ProjectEntity> {
        return projectDao.search(searchQuery)
    }
}