package domain.repository_interfaces

import data.local.entities.NotificationEntity
import kotlinx.coroutines.flow.Flow


interface NotificationRepository {
     suspend fun upsertAll(notifications : List<NotificationEntity>)
     fun getAllNotificationsByProjectIDAsFlow(projectId : String): Flow<List<NotificationEntity>>
     fun getAllNotificationsAsFlow(): Flow<List<NotificationEntity>>
     suspend fun getNotificationById(notificationId : String): NotificationEntity
     suspend fun delete(notification : NotificationEntity)
     suspend fun deleteAllNotifications()
}