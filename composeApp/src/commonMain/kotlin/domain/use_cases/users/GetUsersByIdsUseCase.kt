package domain.use_cases.users

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow


class GetUsersByIdsUseCase (
    private val repository: UserRepository
){
    operator fun invoke(ids : List<String>): Flow<List<UserEntity>> {
        return repository.getAllUsersOfTheseIds(ids)
    }
}