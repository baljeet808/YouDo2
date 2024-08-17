package common

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlin.random.Random

fun getRandomAvatar(): String {
    val randomInt = Random.nextInt(from = 0, 6)
    return avatars[randomInt]
}

fun getRandomColor(): EnumProjectColors {
    val randomInt = Random.nextInt(from = 0, 9)
    return EnumProjectColors.entries[randomInt]
}


fun getRandomColorEnum(): EnumProjectColors {
    val randomInt = Random.nextInt(from = 0, 9)
    return EnumProjectColors.entries[randomInt]
}

expect fun getRandomId(): String


expect fun getCurrentTimeZone() : TimeZone

fun getSampleDateInLong(): Long {
    val currentDateTime = Clock.System.now()
    return currentDateTime.toEpochMilliseconds()
}

fun getRandomLoginHeading() : String {
    return loginHeadings[Random.nextInt(0, loginHeadings.size-1)]
}

fun getRandomPasswordPlaceholder() : String {
    return passwordPlaceholders[Random.nextInt(0, passwordPlaceholders.size-1)]
}

fun getRandomSignUpHeading() : String {
    return signupHeadings[Random.nextInt(0, signupHeadings.size-1)]
}
