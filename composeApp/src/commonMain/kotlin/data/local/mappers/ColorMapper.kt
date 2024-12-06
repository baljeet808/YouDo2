package data.local.mappers

import data.local.entities.ColorPaletteEntity
import domain.models.ColorPalette


fun ColorPaletteEntity.toColorPalette() : ColorPalette {
    return ColorPalette(
        id = id,
        paletteName = paletteName,
        colorLongValue = colorLongValue,
        sortOrder = sortOrder
    )
}

fun ColorPalette.toColorPaletteEntity() : ColorPaletteEntity {
    return ColorPaletteEntity(
        id = id,
        paletteName = paletteName,
        colorLongValue = colorLongValue,
        sortOrder = sortOrder
    )
}