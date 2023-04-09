package az.pashabank.domain.usecase.login

import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.AuthRepository
import kotlin.coroutines.CoroutineContext

class LoginUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: AuthRepository
) : BaseUseCase<LoginUseCase.Param, Boolean>(context, converter) {

    override suspend fun executeOnBackground(params: Param): Boolean {
        return repository.masterLogin(params.email, params.password)
    }

    data class Param(val email: String, val password: String)
}

