package domain.repository_interfaces

import data.local.entities.TaskEntity
import data.local.relations.TaskWithProject
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
     suspend fun upsertAll(tasks : List<TaskEntity>)
     suspend fun getAllTasksByProjectID(projectId : String): List<TaskEntity>
     fun getAllTasksByProjectIDAsFlow(projectId : String): Flow<List<TaskEntity>>
     fun getAllTasks(): Flow<List<TaskEntity>>
     fun getAllTasksWithProjectAsFlow(): Flow<List<TaskWithProject>>
     suspend fun getAllTasksWithProject(): List<TaskWithProject>
     suspend fun getTaskById(taskId : String): TaskEntity
     fun getTaskByIdAsAFlow(taskId : String): Flow<TaskEntity>
     suspend fun delete(task : TaskEntity)
     suspend fun deleteAllByProjectId(projectId: String)
     fun getYesterdayTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>
     fun getTodayTasks(todayDateInLong: Long): Flow<List<TaskEntity>>
     fun getTomorrowTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>>
     fun getPendingTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>
     fun getAllOtherTasks(tomorrowDateInLong: Long ): Flow<List<TaskEntity>>
}