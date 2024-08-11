package domain.use_cases.auth_use_cases

import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class SignOutUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Flow<Result<Boolean, DataError.Network>> = flow {
        try {
            val result = authRepository.signOut()
            if(result){
                emit(Result.Success(result))
            }else{
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