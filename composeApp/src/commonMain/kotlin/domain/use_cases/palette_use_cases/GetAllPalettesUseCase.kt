package domain.use_cases.palette_use_cases

import data.local.entities.ColorPaletteEntity
import domain.repository_interfaces.ColorPaletteRepository

class GetAllPalettesUseCase(
    private val repository: ColorPaletteRepository
){
    suspend operator fun invoke(): List<ColorPaletteEntity> {
        return repository.getAllPalettes()
    }
}