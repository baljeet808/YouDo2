package common

import kotlinx.datetime.TimeZone
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

actual fun getCurrentTimeZone():TimeZone =
    NSTimeZone.localTimeZone.toKotlinTimeZone()

fun NSTimeZone.toKotlinTimeZone(): TimeZone = TimeZone.of(identifier)