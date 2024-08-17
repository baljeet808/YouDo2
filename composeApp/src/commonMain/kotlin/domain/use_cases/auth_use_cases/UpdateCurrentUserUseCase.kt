package domain.use_cases.auth_use_cases

import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class UpdateCurrentUserUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name :String, avatarUrl : String) : Flow<Result<Boolean, DataError.Network>> = flow {
        try {
            authRepository.updateCurrentUser(name = name, avatarUrl = avatarUrl)
            emit(Result.Success(true))
        }
        catch (e: IOException) {
            emit(Result.Error(DataError.Network.NO_INTERNET))
        }
        catch (e : Exception){
            emit(Result.Error(DataError.Network.ALL_OTHER))
        }
    }
}