package common

import java.util.UUID

actual fun getRandomId(): String {
    return UUID.randomUUID().toString()
}