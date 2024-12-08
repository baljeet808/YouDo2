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
import domain.use_cases.user_use_cases.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CodeGeneratorViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(CodeGeneratorUIState())
        private set

    fun onEvent(event : CodeGeneratorScreenEvent) {
        when(event) {
            is CodeGeneratorScreenEvent.RegenerateCode -> {
                generateNewCode(userId = event.userId)
            }
        }
    }


    fun setInitialCode(code : String) {
        uiState = uiState.copy(
            code = code
        )
    }
    private fun generateRandomSixDigitNumber(): Int {
        return (100000..999999).random()
    }

    private fun generateNewCode(userId: String)  {
        viewModelScope.launch(Dispatchers.IO){
            val newCode = generateRandomSixDigitNumber().toString()
            try {
                getUserByIdUseCase(userId)?.let { user ->
                    user.sharingCcode = newCode
                    Firebase.firestore.collection("user")
                        .document(userId).set(user.toUser())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(error = DataError.Network.NO_INTERNET, isLoading = false)
                }
            } finally {
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

