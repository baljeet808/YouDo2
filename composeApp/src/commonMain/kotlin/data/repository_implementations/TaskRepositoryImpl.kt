package data.repository_implementations

import data.local.entities.TaskEntity
import data.local.relations.TaskWithProject
import data.local.room.YouDo2Database
import domain.repository_interfaces.TaskRepository
import kotlinx.coroutines.flow.Flow


class TaskRepositoryImpl(
    localDB: YouDo2Database
) : TaskRepository {

    private val doToosDao = localDB.taskDao()

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return doToosDao.getAllTasks()
    }

    override fun getAllTasksWithProjectAsFlow(): Flow<List<TaskWithProject>> {
        return doToosDao.getAllTasksWithProjectAsFlow()
    }

    override suspend fun getAllTasksWithProject(): List<TaskWithProject> {
        return doToosDao.getAllTasksWithProject()
    }

    override suspend fun upsertAll(tasks: List<TaskEntity>) {
        return doToosDao.upsertAll(tasks)
    }

    override suspend fun getAllTasksByProjectID(projectId: String): List<TaskEntity> {
        return doToosDao.getAllTasksByProjectID(projectId = projectId)
    }

    override fun getAllTasksByProjectIDAsFlow(projectId: String): Flow<List<TaskEntity>> {
        return doToosDao.getAllTasksByProjectIDAsFlow(projectId = projectId)
    }

    override suspend fun getTaskById(taskId: String): TaskEntity {
        return doToosDao.getTaskById(taskId)
    }

    override fun getTaskByIdAsAFlow(taskId: String): Flow<TaskEntity> {
        return doToosDao.getTaskByIdAsAFlow(taskId)
    }

    override suspend fun delete(task: TaskEntity) {
        return doToosDao.delete(task)
    }

    override fun getYesterdayTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>> {
        return doToosDao.getYesterdayTasks(yesterdayDateInLong)
    }

    override fun getTodayTasks(todayDateInLong: Long): Flow<List<TaskEntity>> {
        return doToosDao.getTodayTasks(todayDateInLong)
    }

    override fun getTomorrowTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>> {
        return doToosDao.getTomorrowTasks(tomorrowDateInLong)
    }

    override fun getPendingTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>> {
        return doToosDao.getPendingTasks(yesterdayDateInLong)
    }

    override fun getAllOtherTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>> {
        return doToosDao.getAllOtherTasks(tomorrowDateInLong)
    }

    override suspend fun deleteAllByProjectId(projectId: String) {
        return doToosDao.deleteAllByProjectId(projectId = projectId)
    }

}