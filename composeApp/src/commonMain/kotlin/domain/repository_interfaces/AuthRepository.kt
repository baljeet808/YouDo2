package domain.repository_interfaces

import data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<UserEntity?>
    val currentUserId: String
    val isAuthenticated: Boolean

    suspend fun authenticate(email: String, password: String)
    suspend fun createUser(email: String, password: String)
    suspend fun signOut()

}