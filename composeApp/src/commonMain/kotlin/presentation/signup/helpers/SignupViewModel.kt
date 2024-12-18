package presentation.signup.helpers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.getRandomAvatar
import common.isEmailValid
import common.isPasswordValid
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.dto_helpers.Result
import domain.models.User
import domain.use_cases.auth_use_cases.SignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class SignupViewModel(
    private val signupUseCase: SignupUseCase,
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(SignupUIState())
        private set

    fun updatePassword(password: String) {
        val passwordValid = password.isPasswordValid()
        uiState = uiState.copy(
            password = password,
            passwordInValid = passwordValid.not(),
            enableSignupButton = passwordValid && uiState.email.isEmailValid()
        )
    }

    fun updateEmail(email: String) {
        val emailValid = email.isEmailValid()
        uiState = uiState.copy(
            email = email,
            emailInValid = emailValid.not(),
            enableSignupButton = emailValid && uiState.password.isPasswordValid()
        )
    }

    fun attemptSignup() {
        if (!uiState.email.isEmailValid()) {
            uiState = uiState.copy(emailInValid = true, enableSignupButton = false)
            return
        }
        if (!uiState.password.isPasswordValid()) {
            uiState = uiState.copy(passwordInValid = true, enableSignupButton = false)
            return
        }
        uiState = uiState.copy(
            emailInValid = false,
            passwordInValid = false,
            isLoading = true,
            enableSignupButton = false
        )
        signup(email = uiState.email, password = uiState.password)
    }

    private fun signup(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        signupUseCase(email, password).collect {
            when (it) {
                is Result.Error -> {
                    withContext(Dispatchers.Main) {
                        uiState = uiState.copy(
                            isLoading = false,
                            error = it.error,
                            enableSignupButton = uiState.password.isPasswordValid() && uiState.email.isEmailValid()
                        )
                    }
                }

                is Result.Success -> {
                    saveToFirebase(it.data)
                }
            }
        }
    }

    private fun saveToFirebase(firebaseUser: FirebaseUser) = viewModelScope.launch(Dispatchers.IO) {
        val newUser = User(
            id = firebaseUser.uid,
            name = firebaseUser.displayName ?: "",
            email = firebaseUser.email ?: "",
            joined = Clock.System.now().epochSeconds,
            avatarUrl = firebaseUser.photoURL ?: getRandomAvatar()
        )
        try {
            Firebase.firestore
                .collection("users")
                .document(newUser.id)
                .set(newUser)
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = null,
                    uid = newUser.id,
                    email = newUser.email,
                    signupSuccessful = true
                )
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = DataError.Network.ALL_OTHER,
                    uid = newUser.id,
                    email =  newUser.email,
                    signupSuccessful = true
                )
            }
        }
    }

}