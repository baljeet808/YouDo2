package domain.use_cases.users

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository


class UpsertUserUseCase (
    private val repository: UserRepository
){
    suspend operator fun invoke(users : List<UserEntity>) {
        repository.upsertAll(users)
    }
}