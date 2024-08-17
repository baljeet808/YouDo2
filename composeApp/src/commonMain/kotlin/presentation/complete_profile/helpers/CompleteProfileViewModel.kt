package presentation.complete_profile.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.mappers.toUserEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.User
import domain.use_cases.user_use_cases.UpsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class CompleteProfileViewModel(
    private val upsertUserUseCase: UpsertUserUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(CompleteProfileUIState())
        private set

    fun updateName(name: String) {
        uiState = uiState.copy(userName = name, enableSaveButton = name.isNotEmpty() && uiState.selectedAvatar.isNotEmpty())
    }

    fun updateAvatarUrl(url: String) {
        uiState = uiState.copy(selectedAvatar = url, enableSaveButton = uiState.userName.isNotEmpty())
    }

    fun attemptSaveProfile(email: String, uid: String) {
        uiState = uiState.copy(isLoading = true)
        updateInFirebase(email = email, uid = uid)
    }

    private fun updateInFirebase(email: String, uid: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                id = uid,
                name = uiState.userName,
                email = email,
                joined = Clock.System.now().epochSeconds,
                avatarUrl = uiState.selectedAvatar
            )

            try {
                Firebase.firestore
                    .collection("users")
                    .document(uid)
                    .set(user)

                upsertUserUseCase(listOf( user.toUserEntity()))

                withContext(Dispatchers.Main) {
                    uiState =
                        uiState.copy(isLoading = false, error = null, savedSuccessfully = true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = DataError.Network.ALL_OTHER,
                        savedSuccessfully = false
                    )
                }
            }
        }
}