import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository_interfaces.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AppViewModel : ViewModel(), KoinComponent {

    private val dataStoreRepository : DataStoreRepository by inject<DataStoreRepository>()

    var userState by mutableStateOf(AppUIState())
        private set

    init {
        checkUserLoggedInStatus()
    }

    private fun checkOnboardingStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.hasOnboardedAsFlow().collect{
                withContext(Dispatchers.Main) {
                    userState = userState.copy(hasOnboarded = it, resultFound = true)
                }
            }
        }
    }

    private fun checkUserLoggedInStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.isUserLoggedInAsFlow().collect {
                if(it){
                    withContext(Dispatchers.Main) {
                        userState = userState.copy(isUserLoggedIn = true, resultFound = true)
                    }
                }else{
                   checkOnboardingStatus()
                }
            }
        }
    }
}

data class AppUIState(
    val isUserLoggedIn : Boolean = false,
    val hasOnboarded : Boolean = false,
    val resultFound : Boolean = false
)
