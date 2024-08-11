package domain.use_cases.auth_use_cases

import data.local.entities.UserEntity
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class GetCurrentUserUseCase (
    private val authRepository: AuthRepository
) {
    operator fun invoke() : Flow<Result<UserEntity, DataError.Network>> = flow {
        try {
            val user = authRepository.currentUser
            user?.let {
                emit(Result.Success(user))
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