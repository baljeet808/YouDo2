package presentation.shared.shareCodeGenerator.helper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toUser
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.User
import domain.use_cases.user_use_cases.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CodeGeneratorViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(CodeGeneratorUIState())
        private set

    fun setInitialCode(user: User) {
        uiState = uiState.copy(
            code = user.sharingCode
        )
    }
    private fun generateRandomSixDigitNumber(): Int {
        return (100000..999999).random()
    }

    fun generateNewCode(userId: String)  {
        uiState = uiState.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO){
            println("TEST : generateNewCode: $userId")
            val newCode = generateRandomSixDigitNumber().toString()
            println("TEST : new code = $newCode")
            try {
                getUserByIdUseCase(userId)?.let { user ->
                    user.sharingCcode = newCode
                    Firebase.firestore.collection("users")
                        .document(userId).set(user.toUser())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(error = DataError.Network.NO_INTERNET, isLoading = false)
                }
            } finally {
                delay(1000)
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(
                        isLoading = false,
                        code = newCode
                    )
                }
            }
        }
    }
}

