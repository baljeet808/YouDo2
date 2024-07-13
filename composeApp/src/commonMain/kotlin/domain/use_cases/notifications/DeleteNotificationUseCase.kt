package domain.use_cases.notifications

import data.local.entities.NotificationEntity
import domain.repository_interfaces.NotificationRepository


class DeleteNotificationUseCase(
    private val repository: NotificationRepository
){
    suspend operator fun invoke(notificationEntity: NotificationEntity)  {
        return repository.delete(notificationEntity)
    }
}