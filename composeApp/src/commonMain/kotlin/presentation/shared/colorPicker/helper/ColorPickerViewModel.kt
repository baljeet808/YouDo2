package presentation.shared.colorPicker.helper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.entities.ColorPaletteEntity
import data.local.mappers.toColorPaletteEntity
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.dto_helpers.DataError
import domain.models.ColorPalette
import domain.use_cases.palette_use_cases.UpsertPalettesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class ColorPickerViewModel(
    private val upsertColorPaletteUseCase: UpsertPalettesUseCase
) : ViewModel(), KoinComponent {

    var uiState by mutableStateOf(ColorPickerUIState())
        private set

    fun onEvent(event : ColorPickerScreenEvent) {
        when(event) {
            is ColorPickerScreenEvent.ColorSelected -> {
                uiState = uiState.copy(
                    selectedColor = event.color
                )
            }
        }
    }

    fun getColorsFromFirebase(initiallySelectedColor : ColorPalette?) {
        uiState = uiState.copy(
            selectedColor = initiallySelectedColor,
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Firebase.firestore.collection("colors")
                    .snapshots.collect { colorsQuerySnapshot ->
                        val colors = colorsQuerySnapshot.documents.map { documentSnapshot ->
                            documentSnapshot.data<ColorPalette>()
                        }
                        updateColorsLocally(colors.map { it.toColorPaletteEntity() })
                        withContext(Dispatchers.Main){
                            uiState = uiState.copy(colorList = colors, isLoading = false)
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    uiState = uiState.copy(error = DataError.Network.NO_INTERNET, isLoading = false)
                }
            }
        }
    }

    private fun updateColorsLocally(color: List<ColorPaletteEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            upsertColorPaletteUseCase(color)
        }
    }
}

