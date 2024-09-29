package data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import data.local.entities.TaskEntity
import data.local.relations.TaskWithProject
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Transaction
    @Upsert
    suspend fun upsertAll(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks WHERE projectId = :projectId")
    suspend fun getAllTasksByProjectID(projectId: String): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE projectId = :projectId")
    fun getAllTasksByProjectIDAsFlow(projectId: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasksWithProjectAsFlow(): Flow<List<TaskWithProject>>

    @Transaction
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasksWithProject(): List<TaskWithProject>

    @Query("SELECT * FROM tasks where id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity

    @Query("SELECT * FROM tasks where id = :taskId")
    fun getTaskByIdAsAFlow(taskId: String): Flow<TaskEntity>

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM tasks where projectId = :projectId")
    suspend fun deleteAllByProjectId(projectId: String)

    @Query("SELECT * FROM tasks WHERE dueDate = :yesterdayDateInLong")
    fun getYesterdayTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate = :todayDateInLong")
    fun getTodayTasks(todayDateInLong: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate = :tomorrowDateInLong")
    fun getTomorrowTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate < :yesterdayDateInLong")
    fun getPendingTasks(yesterdayDateInLong: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueDate > :tomorrowDateInLong")
    fun getAllOtherTasks(tomorrowDateInLong: Long): Flow<List<TaskEntity>>

}