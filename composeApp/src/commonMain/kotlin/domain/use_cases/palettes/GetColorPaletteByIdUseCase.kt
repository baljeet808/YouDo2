package domain.use_cases.palettes


import data.local.entities.ColorPaletteEntity
import domain.repository_interfaces.ColorPaletteRepository

class GetColorPaletteByIdUseCase(
    private val repository: ColorPaletteRepository
){
    suspend operator fun invoke(paletteId : String): ColorPaletteEntity? {
        return repository.getColorPaletteById(paletteId)
    }
}