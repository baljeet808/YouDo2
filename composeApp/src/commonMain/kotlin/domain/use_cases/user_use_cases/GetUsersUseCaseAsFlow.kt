package domain.use_cases.user_use_cases

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCaseAsFlow (
    private val repository: UserRepository
){
    operator fun invoke(): Flow<List<UserEntity>> {
        return repository.getAllUsersAsFlow()
    }
}