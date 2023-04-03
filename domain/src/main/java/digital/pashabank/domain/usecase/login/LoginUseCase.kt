package digital.pashabank.domain.usecase.login

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.AuthRepository
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

