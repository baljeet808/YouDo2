package domain.repository_interfaces

import data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow


interface UserRepository {
     suspend fun upsertAll(users : List<UserEntity>)
     fun getAllUsersAsFlow(): Flow<List<UserEntity>>
     suspend fun getAllUsers(): List<UserEntity>
     suspend fun getUserById(userId : String): UserEntity?
     fun getUserByIdAsAFlow(userId : String): Flow<UserEntity?>
     fun getAllUsersOfTheseIds(userIds : List<String>) : Flow<List<UserEntity>>
     suspend fun delete(user : UserEntity)
}