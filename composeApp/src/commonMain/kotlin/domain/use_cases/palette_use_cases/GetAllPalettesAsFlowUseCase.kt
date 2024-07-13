package domain.use_cases.palette_use_cases

import data.local.entities.ColorPaletteEntity
import domain.repository_interfaces.ColorPaletteRepository
import kotlinx.coroutines.flow.Flow

class GetAllPalettesAsFlowUseCase(
    private val repository: ColorPaletteRepository
){
    operator fun invoke(): Flow<List<ColorPaletteEntity>> {
        return repository.getAllPalettesAsFlow()
    }
}