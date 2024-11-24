package data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Upsert
    suspend fun upsertAll(users : List<UserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers() : List<UserEntity>

    @Query("SELECT * FROM users")
    fun getAllUsersAsFlow() : Flow<List<UserEntity>>

    @Query("SELECT * FROM users where id = :userId ")
    suspend fun getUserById(userId : String) : UserEntity?

    @Query("SELECT * FROM users where id = :userId ")
    fun getUserByIdAsAFlow(userId : String) : Flow<UserEntity?>

    @Query("SELECT * FROM users where id IN (:userIds)")
    fun getAllUsersOfTheseIdsAsFlow(userIds : List<String>) : Flow<List<UserEntity>>

    @Query("SELECT * FROM users where id IN (:userIds)")
    suspend fun getAllUsersOfTheseIds(userIds : List<String>) : List<UserEntity>

    @Delete
    suspend fun delete(user : UserEntity)

}