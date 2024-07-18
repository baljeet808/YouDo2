package data.repository_implementations

import data.local.entities.UserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Updated by Baljeet singh.
 * **/
class AuthRepositoryImpl(
    private val auth : FirebaseAuth = Firebase.auth,
    private val scope : CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
): AuthRepository {

    override val currentUser: Flow<UserEntity?>
        get() = auth.authStateChanged.map {
            it?.let {
                 UserEntity(
                     id = it.uid,
                     email = it.email.toString(),
                     name = it.displayName.toString(),
                     avatarUrl = it.photoURL.toString(),
                     firebaseToken = "",
                     joined = it.metaData?.creationTime?.toLong()?: 0L,
                 )
            }
        }
    override val currentUserId: String
        get() = auth.currentUser?.uid.toString()
    override val isAuthenticated: Boolean
        get() = auth.currentUser != null && auth.currentUser?.isAnonymous == false

    private suspend fun launchWithAwait(block: suspend () -> Unit) {
        scope.async {
            block()
        }.await()
    }

    override suspend fun authenticate(email: String, password: String) {
        launchWithAwait {
            auth.signInWithEmailAndPassword(
                email = email,
                password = password
            )
        }
    }

    override suspend fun createUser(email: String, password: String) {
        launchWithAwait {
            auth.createUserWithEmailAndPassword(
                email = email,
                password = password
            )
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}