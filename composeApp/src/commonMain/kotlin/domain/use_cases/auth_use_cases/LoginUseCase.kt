package domain.use_cases.auth_use_cases

import domain.repository_interfaces.AuthRepository

class LoginUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = authRepository.authenticate(email, password)
}