package presentation.shared.colorPicker.helper

import domain.models.ColorPalette

sealed class ColorPickerScreenEvent {
    data class ColorSelected(val color: ColorPalette) : ColorPickerScreenEvent()
}