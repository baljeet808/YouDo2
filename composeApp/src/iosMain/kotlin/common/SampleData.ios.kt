package common

import kotlinx.datetime.TimeZone

actual fun getCurrentTimeZone():TimeZone =
    TimeZone.currentSystemDefault()
