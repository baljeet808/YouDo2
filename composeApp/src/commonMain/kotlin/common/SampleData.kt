package common

import androidx.compose.ui.graphics.Color
import data.local.entities.ColorPaletteEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlin.random.Random

fun getRandomAvatar(): String {
    val randomInt = Random.nextInt(from = 0, 6)
    val avatars = arrayListOf<String>(
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/20.png?alt=media&token=fa1489d4-8951-4ef6-8f96-862938aedb62",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/1.png?alt=media&token=b15d14e9-722d-410d-b9b4-23682b5773f3",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/2.png?alt=media&token=68e95bc9-9553-4027-90d9-af688b9fd0f4",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/16.png?alt=media&token=160bd284-4b6a-488c-b66b-b421ebde9c21",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/4.png?alt=media&token=3c06a69a-7f00-4238-b6cf-3296f3532576",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/6.png?alt=media&token=3fefb778-d6a4-4c05-9d0b-ed8dde6091e5",
        "https://firebasestorage.googleapis.com/v0/b/youdotoo-81372.appspot.com/o/9.png?alt=media&token=dd24b271-e068-43a6-b0f4-c3142873575c"
    )
    return avatars[randomInt]
}

fun getRandomColor(): String {
    val randomInt = Random.nextInt(from = 0, 9)
    return EnumProjectColors.entries[randomInt].name
}


fun getRandomColorEnum(): EnumProjectColors {
    val randomInt = Random.nextInt(from = 0, 9)
    return EnumProjectColors.entries[randomInt]
}

fun getSampleColorPalette(): ColorPaletteEntity {
    return ColorPaletteEntity(
        id = getRandomId(),
        paletteName = "Randomized",
        nightDark = getRandomColor().getColor().value.toLong(),
        nightLight = getRandomColor().getColor().value.toLong(),
        dayDark = getRandomColor().getColor().value.toLong(),
        dayLight = getRandomColor().getColor().value.toLong()
    )
}

expect fun getRandomId(): String

fun String.getColor(): Color {
    return when(this){
        EnumProjectColors.Green.name -> Color(0xFF006261)
        EnumProjectColors.Pink.name -> Color(0xFFFF69B4)
        EnumProjectColors.Blue.name -> Color(0xff363CB5)
        EnumProjectColors.Red.name -> Color(0xFFF53C4F)
        EnumProjectColors.Yellow.name -> Color(0xFFFF8526)
        EnumProjectColors.Brown.name -> Color(0xFFA52A2A)
        EnumProjectColors.Black.name -> Color(0xFF302D2D)
        EnumProjectColors.Cyan.name -> Color(0xFF8BDFFE)
        EnumProjectColors.Indigo.name -> Color(0xFF4b0082)
        else -> {
            Color(0xFF4b0082)
        }
    }
}

expect fun getCurrentTimeZone() : TimeZone

fun getSampleDateInLong(): Long {
    val currentDateTime = Clock.System.now()
    return currentDateTime.toEpochMilliseconds()
}


