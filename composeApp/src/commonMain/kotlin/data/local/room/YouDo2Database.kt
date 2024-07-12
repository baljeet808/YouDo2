package data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.local.converters.NotificationTypeConverter
import data.local.daos.ColorPaletteDao
import data.local.daos.DoTooItemDao
import data.local.daos.InvitationDao
import data.local.daos.MessageDao
import data.local.daos.NotificationDao
import data.local.daos.ProjectDao
import data.local.daos.UserDao
import data.local.entities.ColorPaletteEntity
import data.local.entities.InvitationEntity
import data.local.entities.MessageEntity
import data.local.entities.NotificationEntity
import data.local.entities.ProjectEntity
import data.local.entities.TaskEntity
import data.local.entities.UserEntity

@Database(
    entities = [
        ProjectEntity::class,
        TaskEntity::class,
        UserEntity::class,
        InvitationEntity::class,
        NotificationEntity::class,
        ColorPaletteEntity::class,
        MessageEntity::class
    ],
    version = 1
)
@TypeConverters(NotificationTypeConverter::class)
abstract class YouDo2Database : RoomDatabase() {
    abstract val projectDao : ProjectDao
    abstract val userDao : UserDao
    abstract val doTooItemDao : DoTooItemDao
    abstract val invitationDao : InvitationDao
    abstract val notificationDao : NotificationDao
    abstract val colorPaletteDao : ColorPaletteDao
    abstract val messageDao : MessageDao
}