package domain.use_cases.notification_use_cases

import domain.repository_interfaces.NotificationRepository


class DeleteAllNotificationsUseCase(
    private val repository: NotificationRepository
){
    suspend operator fun invoke()  {
        return repository.deleteAllNotifications()
    }
}