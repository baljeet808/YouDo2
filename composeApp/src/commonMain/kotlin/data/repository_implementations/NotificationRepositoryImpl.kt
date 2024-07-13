package data.repository_implementations

import data.local.entities.NotificationEntity
import data.local.room.YouDo2Database
import domain.repository_interfaces.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(localDB: YouDo2Database) : NotificationRepository {

    private val notificationDao = localDB.notificationDao()

    override suspend fun upsertAll(notifications: List<NotificationEntity>) {
        return notificationDao.upsertAll(notifications)
    }

    override fun getAllNotificationsByProjectIDAsFlow(projectId: String): Flow<List<NotificationEntity>> {
        return notificationDao.getAllNotificationsByProjectIDAsFlow(projectId)
    }

    override fun getAllNotificationsAsFlow(): Flow<List<NotificationEntity>> {
        return notificationDao.getAllNotificationsAsFlow()
    }

    override suspend fun getNotificationById(notificationId: String): NotificationEntity {
        return notificationDao.getNotificationById(notificationId)
    }

    override suspend fun delete(notification: NotificationEntity) {
        return notificationDao.delete(notification)
    }

    override suspend fun deleteAllNotifications() {
        notificationDao.deleteAllNotifications()
    }

}