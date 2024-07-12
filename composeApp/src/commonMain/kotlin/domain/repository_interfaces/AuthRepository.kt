package domain.repository_interfaces

import dev.gitlive.firebase.auth.UserInfo
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    val currentUserId: String
    val isAuthenticated: Boolean
    val currentUser: Flow<UserInfo>

    suspend fun authenticate()

    suspend fun createUser()

    suspend fun signOut()
}