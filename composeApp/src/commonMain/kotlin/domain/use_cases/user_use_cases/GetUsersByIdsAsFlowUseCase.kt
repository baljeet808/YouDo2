package domain.use_cases.user_use_cases

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow


class GetUsersByIdsAsFlowUseCase (
    private val repository: UserRepository
){
    operator fun invoke(ids : List<String>): Flow<List<UserEntity>> {
        return repository.getAllUsersOfTheseIdsAsFlow(ids)
    }
}