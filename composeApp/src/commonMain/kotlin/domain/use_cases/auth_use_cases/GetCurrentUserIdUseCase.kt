package domain.use_cases.auth_use_cases

import domain.repository_interfaces.AuthRepository

class GetCurrentUserIdUseCase (
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.currentUserId
}