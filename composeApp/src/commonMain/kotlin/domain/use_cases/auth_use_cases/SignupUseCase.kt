package domain.use_cases.auth_use_cases

import domain.repository_interfaces.AuthRepository

class SignupUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = authRepository.createUser(email, password)
}