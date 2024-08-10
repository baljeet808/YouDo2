package domain.use_cases.auth_use_cases

import dev.gitlive.firebase.auth.FirebaseUser
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class SignupUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) : Flow<Result<FirebaseUser, DataError.Network>> = flow {
        try {
            val result = authRepository.createUser(email, password)
            result?.let { user ->
                emit(Result.Success(user))
            }?:run {
                emit(Result.Error(DataError.Network.ALL_OTHER))
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