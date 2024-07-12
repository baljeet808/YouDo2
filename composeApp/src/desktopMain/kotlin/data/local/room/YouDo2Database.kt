package data.local.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File


fun getYouDo2Database(): YouDo2Database {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "YouDo2.db")
    return Room.databaseBuilder<YouDo2Database>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
        .build()
}