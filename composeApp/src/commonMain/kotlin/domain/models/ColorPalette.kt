package domain.models


import kotlinx.serialization.Serializable

@Serializable
data class ColorPalette(
    var id : String = "",
    var paletteName : String = "",
    var colorLongValue : Long = 0,
)
