package data.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<YouDo2Database> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(YouDo2Database.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}