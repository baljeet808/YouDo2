package presentation.onboarding.helpers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository_interfaces.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class OnBoardingViewModel(
    private val dataStoreRepository: DataStoreRepository
): ViewModel(), KoinComponent {

    fun setOnboardingStatus(isComplete : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveHasOnboarded(isComplete)
        }
    }

}