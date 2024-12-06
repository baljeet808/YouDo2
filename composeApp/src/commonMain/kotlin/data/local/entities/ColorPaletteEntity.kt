package data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "palettes")
data class ColorPaletteEntity(
    @PrimaryKey
    var id : String,
    var paletteName : String,
    var colorLongValue : Long,
    var sortOrder : Int
)