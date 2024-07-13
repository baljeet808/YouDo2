package data.repository_implementations

import data.local.entities.UserEntity
import data.local.room.YouDo2Database
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow


class UserRepositoryImpl(localDB: YouDo2Database) : UserRepository {

    private val userDao = localDB.userDao()

    override suspend fun upsertAll(users: List<UserEntity>) {
        return userDao.upsertAll(users)
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    override suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }

    override fun getUserByIdAsAFlow(userId: String): Flow<UserEntity?> {
        return userDao.getUserByIdAsAFlow(userId)
    }

    override fun getAllUsersOfTheseIds(userIds: List<String>): Flow<List<UserEntity>> {
        return userDao.getAllUsersOfTheseIds(userIds)
    }

    override suspend fun delete(user: UserEntity) {
        return userDao.delete(user)
    }
}