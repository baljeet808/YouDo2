package presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.use_cases.auth_use_cases.GetCurrentUserUseCase
import domain.use_cases.auth_use_cases.SignOutUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DashboardViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel(), KoinComponent{

    private val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        println("DashboardViewModel: Error ${throwable.message}")
        uiState = uiState.copy(error = "Invalid credentials. Please try again!")
    }
    private var job: Job? = null
    private fun launchWithCatchingException(block : suspend CoroutineScope.() -> Unit){
        job = viewModelScope.launch(
            context = coroutineContext,
            block = block
        )
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        uiState = DashboardUIState()
    }

    var uiState by mutableStateOf(DashboardUIState())
        private set

    init {
        launchWithCatchingException {
            getCurrentUserUseCase() .collectLatest { user ->
                user?.let {
                    uiState = uiState.copy(userEmail = user.email, userName = user.name, userId = user.id, userAvatarUrl = user.avatarUrl)
                }?: run {
                    uiState = uiState.copy(userEmail = "", userName = "", userId = "", userAvatarUrl = "")
                }
            }
        }
    }

    private fun onLogout(){
        launchWithCatchingException {
            signOutUseCase()
        }
    }

    fun onEvent(event : DashboardScreenEvents){
        when(event){
            is DashboardScreenEvents.OnAttemptToLogout -> {
                uiState = uiState.copy(isLoggingOut = true)
                onLogout()
                uiState = uiState.copy(isLoggingOut = false)
                validateLogout()
            }

            DashboardScreenEvents.OnRefreshUIState ->{
                uiState = DashboardUIState()
            }
        }
    }

    private fun validateLogout() {
        Firebase.auth.currentUser?.let {
            uiState = uiState.copy(error = "Something went wrong, while trying to logout.", isLoggedOut = false)
        }?: run {
            uiState = uiState.copy( isLoggedOut = true)
        }
    }

}