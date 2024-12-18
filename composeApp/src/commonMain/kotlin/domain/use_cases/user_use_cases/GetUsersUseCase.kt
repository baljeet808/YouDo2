package domain.use_cases.user_use_cases

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository

class GetUsersUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(): List<UserEntity> {
        return repository.getAllUsers()
    }
}