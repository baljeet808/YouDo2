package data.local.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver


fun getYouDo2Database(context : Context): YouDo2Database {
    val dbFile = context.getDatabasePath("YouDo2.db")
    return Room.databaseBuilder<YouDo2Database>(
        context,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .build()
}