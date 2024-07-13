package domain.use_cases.notifications

import data.local.entities.NotificationEntity
import domain.repository_interfaces.NotificationRepository

class UpsertNotificationsUseCase(
    private val repository: NotificationRepository
){
    suspend operator fun invoke(notifications: List<NotificationEntity> ) {
        return repository.upsertAll(notifications)
    }
}