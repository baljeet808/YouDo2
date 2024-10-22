package presentation.shared.colorPicker.helper

import domain.dto_helpers.DataError
import domain.models.ColorPalette

data class ColorPickerUIState(
    val isLoading : Boolean = false,
    val error : DataError.Network? = null,
    val colorList : List<ColorPalette> = emptyList(),
    val selectedColor : ColorPalette? = null
)
