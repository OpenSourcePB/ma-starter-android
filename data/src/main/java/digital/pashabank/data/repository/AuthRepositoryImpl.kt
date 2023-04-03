package digital.pashabank.data.repository

import digital.pashabank.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    companion object {
        private const val EXPECTED_EMAIL = "test@gmail.com"
        private const val EXPECTED_PASSWORD = "test123321"
    }

    override suspend fun masterLogin(email: String, password: String): Boolean {
        return email == EXPECTED_EMAIL && password == EXPECTED_PASSWORD
    }
}