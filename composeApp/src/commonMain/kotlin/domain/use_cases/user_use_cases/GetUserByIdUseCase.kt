package domain.use_cases.user_use_cases

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository


class GetUserByIdUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(userId : String): UserEntity? {
        return repository.getUserById(userId)
    }
}