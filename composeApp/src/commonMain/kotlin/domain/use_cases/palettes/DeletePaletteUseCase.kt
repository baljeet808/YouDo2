package domain.use_cases.palettes

import data.local.entities.ColorPaletteEntity
import domain.repository_interfaces.ColorPaletteRepository

class DeletePaletteUseCase(
    private val repository: ColorPaletteRepository
){
    suspend operator fun invoke(palette : ColorPaletteEntity) {
        repository.delete(palette)
    }
}