package domain.use_cases.user_use_cases

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository


class GetUsersByIdsUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(ids : List<String>): List<UserEntity> {
        return repository.getAllUsersOfTheseIds(ids)
    }
}