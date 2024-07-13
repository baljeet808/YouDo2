package domain.use_cases.notifications

import domain.repository_interfaces.NotificationRepository


class DeleteAllNotificationsUseCase(
    private val repository: NotificationRepository
){
    suspend operator fun invoke()  {
        return repository.deleteAllNotifications()
    }
}