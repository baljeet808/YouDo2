package domain.use_cases.palettes


import data.local.entities.ColorPaletteEntity
import domain.repository_interfaces.ColorPaletteRepository

class UpsertPalettesUseCase(
    private val repository: ColorPaletteRepository
){
    suspend operator fun invoke(palettes : List<ColorPaletteEntity>) {
        repository.upsertAll(palettes)
    }
}