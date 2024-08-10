package domain.repository_interfaces

import data.local.entities.UserEntity
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<UserEntity?>
    val currentUserId: String
    val isAuthenticated: Boolean

    suspend fun authenticate(email: String, password: String) : FirebaseUser?
    suspend fun createUser(email: String, password: String) : FirebaseUser?
    suspend fun signOut() : Boolean

}