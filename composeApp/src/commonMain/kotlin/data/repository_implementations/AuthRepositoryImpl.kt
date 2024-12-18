package data.repository_implementations

import data.local.entities.UserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AuthRepositoryImpl(
    private val auth : FirebaseAuth = Firebase.auth,
): AuthRepository {

    override val currentUser: UserEntity?
        get() = auth.currentUser?.let {
            UserEntity(
                id = it.uid,
                email = it.email.toString(),
                name = it.displayName.toString(),
                avatarUrl = it.photoURL.toString(),
                firebaseToken = "",
                joined = it.metaData?.creationTime?.toLong()?: 0L,
            )
        }

    override val currentUserInFlow: Flow<UserEntity?>
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



    override suspend fun authenticate(email: String, password: String) : FirebaseUser? {
        val results = auth.signInWithEmailAndPassword(
            email = email,
            password = password
        )
        return results.user
    }

    override suspend fun createUser(email: String, password: String): FirebaseUser? {
        val results = auth.createUserWithEmailAndPassword(
            email = email,
            password = password
        )
        return results.user
    }

    override suspend fun signOut() : Boolean {
        auth.signOut()
        return auth.currentUser == null
    }

    override suspend fun updateCurrentUser(name: String, avatarUrl: String) {
        auth.currentUser?.apply {
            updateProfile(
                displayName = name,
                photoUrl = avatarUrl
            )
        }
    }

}