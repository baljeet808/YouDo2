package domain.use_cases.notification_use_cases

import data.local.entities.NotificationEntity
import domain.repository_interfaces.NotificationRepository

class GetNotificationByIdUseCase(
    private val repository: NotificationRepository
){
    suspend operator fun invoke(id : String): NotificationEntity {
        return repository.getNotificationById(id)
    }
}