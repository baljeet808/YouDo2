package data.repository_implementations

import data.local.entities.ColorPaletteEntity
import data.local.room.YouDo2Database
import domain.repository_interfaces.ColorPaletteRepository
import kotlinx.coroutines.flow.Flow


class ColorPaletteRepositoryImpl (
    localDB: YouDo2Database
) : ColorPaletteRepository {

    private val paletteDao = localDB.colorPaletteDao()

    override suspend fun upsertAll(palettes : List<ColorPaletteEntity>) {
        paletteDao.upsertAll(palettes)
    }

    override fun getAllPalettesAsFlow(): Flow<List<ColorPaletteEntity>> {
        return  paletteDao.getAllPalettesAsFlow()
    }

    override suspend fun getAllPalettes(): List<ColorPaletteEntity> {
        return paletteDao.getAllPalettes()
    }

    override suspend fun getColorPaletteById(paletteId: String): ColorPaletteEntity? {
        return paletteDao.getColorPaletteById(paletteId)
    }

    override suspend fun delete(palette: ColorPaletteEntity) {
        paletteDao.delete(palette)
    }
}