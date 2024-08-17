package domain.use_cases.auth_use_cases

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.models.User
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class LoginUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) : Flow<Result<User,DataError.Network>> = flow {
        try {
            val result = authRepository.authenticate(email, password)
            result?.let { user ->
                val fireStoreUser = Firebase.firestore.collection("users").document(user.uid).get().data<User> ()
                emit(Result.Success(fireStoreUser))
            }?:run {
                emit(Result.Error(DataError.Network.NOT_FOUND))
            }
        }
        catch (e: IOException) {
            emit(Result.Error(DataError.Network.NO_INTERNET))
        }
        catch (e : Exception){
            emit(Result.Error(DataError.Network.ALL_OTHER))
        }
    }
}