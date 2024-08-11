package common

import kotlinx.datetime.TimeZone
import java.util.UUID

actual fun getRandomId(): String {
    return UUID.randomUUID().toString()
}

actual fun getCurrentTimeZone(): TimeZone {
    return TimeZone.currentSystemDefault()
}