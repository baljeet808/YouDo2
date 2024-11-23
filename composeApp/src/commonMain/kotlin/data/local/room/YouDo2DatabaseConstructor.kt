package data.local.room

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object YouDo2DatabaseConstructor : RoomDatabaseConstructor<YouDo2Database> {
    override fun initialize(): YouDo2Database
}