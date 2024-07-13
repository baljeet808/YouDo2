package domain.use_cases.notifications

import data.local.entities.NotificationEntity
import domain.repository_interfaces.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsByIdAsFlowUseCase(
    private val repository: NotificationRepository
){
    operator fun invoke(projectId : String): Flow<List<NotificationEntity>> {
        return repository.getAllNotificationsByProjectIDAsFlow(projectId)
    }
}