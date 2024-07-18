package common

import platform.Foundation.NSUUID

actual fun getRandomId(): String {
    return NSUUID().UUIDString()
}