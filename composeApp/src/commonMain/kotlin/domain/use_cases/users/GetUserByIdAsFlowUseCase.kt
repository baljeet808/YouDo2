package domain.use_cases.users

import data.local.entities.UserEntity
import domain.repository_interfaces.UserRepository
import kotlinx.coroutines.flow.Flow


class GetUserByIdAsFlowUseCase (
    private val repository: UserRepository
){
    operator fun invoke(userId : String): Flow<UserEntity?> {
        return repository.getUserByIdAsAFlow(userId)
    }
}