package az.pashabank.domain.repository

interface AuthRepository {

    suspend fun masterLogin(email: String, password: String):Boolean

}