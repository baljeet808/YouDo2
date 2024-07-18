package presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entities.UserEntity
import domain.use_cases.auth_use_cases.GetCurrentUserUseCase
import domain.use_cases.auth_use_cases.LoginUseCase
import domain.use_cases.auth_use_cases.SignupUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel(){

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        launchWithCatchingException {
            getCurrentUserUseCase().collect{
                _currentUser.value = it
            }
        }
    }

    private val coroutineContext = SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        println("LoginViewModel: Error ${throwable.message}")
        // you can use this for showing api errors
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
    }

    fun onLogin(email: String, password: String){
        launchWithCatchingException {
            signupUseCase(email, password)
            loginUseCase(email, password)
        }
    }
}