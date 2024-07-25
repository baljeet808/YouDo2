package data.local.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getYouDo2Database(): YouDo2Database {
    val dbFile = NSHomeDirectory() + "/YouDo2.db"
    return Room.databaseBuilder<YouDo2Database>(
        name = dbFile,
        factory = { YouDo2Database::class.instantiateImpl()}
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}