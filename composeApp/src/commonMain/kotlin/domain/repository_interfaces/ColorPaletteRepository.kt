package domain.repository_interfaces

import data.local.entities.ColorPaletteEntity
import kotlinx.coroutines.flow.Flow


interface ColorPaletteRepository {
     suspend fun upsertAll(palettes : List<ColorPaletteEntity>)
     fun getAllPalettesAsFlow(): Flow<List<ColorPaletteEntity>>
     suspend fun getAllPalettes(): List<ColorPaletteEntity>
     suspend fun getColorPaletteById(paletteId : String): ColorPaletteEntity?
     suspend fun delete(palette : ColorPaletteEntity)
}