package domain.use_cases.notification_use_cases

import data.local.entities.NotificationEntity
import domain.repository_interfaces.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotificationsAsFlowUseCase(
    private val repository: NotificationRepository
){
    operator fun invoke(): Flow<List<NotificationEntity>> {
        return repository.getAllNotificationsAsFlow()
    }
}