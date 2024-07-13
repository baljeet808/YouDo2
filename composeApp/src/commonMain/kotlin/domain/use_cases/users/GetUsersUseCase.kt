package domain.use_cases.users

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase (
    private val repository: UserRepository
){
    operator fun invoke(): Flow<List<UserEntity>> {
        return repository.getAllUsers()
    }
}