package domain.repository_interfaces

import data.local.entities.TaskEntity
import data.local.relations.TaskWithProject
import kotlinx.coroutines.flow.Flow

interface DoTooItemsRepository {
     suspend fun upsertAll(tasks : List<TaskEntity>)
     suspend fun getAllDoTooItemsByProjectID(projectId : String): List<TaskEntity>
     fun getAllDoTooItemsByProjectIDAsFlow(projectId : String): Flow<List<TaskEntity>>
     fun getAllDoTooItems(): Flow<List<TaskEntity>>
     fun getAllTasksWithProjectAsFlow(): Flow<List<TaskWithProject>>
     suspend fun getAllTasksWithProject(): List<TaskWithProject>
     suspend fun getDoTooById(doTooId : String): TaskEntity
     fun getTaskByIdAsAFlow(taskId : String): Flow<TaskEntity>
     suspend fun delete(task : TaskEntity)
     suspend fun deleteAllByProjectId(projectId: String)
     fun getYesterdayTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>
     fun getTodayTasks(todayDateInLong: Long): Flow<List<TaskEntity>>
     fun getTomorrowTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>>
     fun getPendingTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>
     fun getAllOtherTasks(tomorrowDateInLong: Long ): Flow<List<TaskEntity>>
}