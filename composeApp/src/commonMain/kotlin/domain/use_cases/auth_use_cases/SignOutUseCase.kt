package domain.use_cases.auth_use_cases

import domain.repository_interfaces.AuthRepository

class SignOutUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = authRepository.signOut()
}