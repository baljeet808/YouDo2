package domain.use_cases.auth_use_cases

import dev.gitlive.firebase.auth.FirebaseUser
import domain.repository_interfaces.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class LoginUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) : Flow<Result<FirebaseUser?>> = flow {
        try {

        }/* catch (e: JsonParseException) {
            emit(Result.Error(DataError.Network.SERIALIZATION))
            Log.e("JsonParseException", e.message.toString())
        }*/ catch (e: IOException) {

        }
    }
}