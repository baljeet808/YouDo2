package domain.use_cases.auth_use_cases

import domain.repository_interfaces.AuthRepository

class IsUserAuthenticatedUseCase (
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.isAuthenticated
}