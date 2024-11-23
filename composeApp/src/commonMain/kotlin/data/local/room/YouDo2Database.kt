package data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.local.converters.NotificationTypeConverter
import data.local.daos.ColorPaletteDao
import data.local.daos.InvitationDao
import data.local.daos.MessageDao
import data.local.daos.NotificationDao
import data.local.daos.ProjectDao
import data.local.daos.TaskDao
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
@ConstructedBy(YouDo2DatabaseConstructor::class)
abstract class YouDo2Database : RoomDatabase(), ClearAllTableFix {
    abstract fun projectDao() : ProjectDao
    abstract fun userDao() : UserDao
    abstract fun taskDao() : TaskDao
    abstract fun invitationDao() : InvitationDao
    abstract fun notificationDao() : NotificationDao
    abstract fun colorPaletteDao() : ColorPaletteDao
    abstract fun messageDao() : MessageDao

    override fun clearAllTables() {
        clearAllTables()
    }
}
interface ClearAllTableFix {
    fun clearAllTables()
}