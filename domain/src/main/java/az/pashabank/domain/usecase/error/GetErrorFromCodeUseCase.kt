package az.pashabank.domain.usecase.error

import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.ErrorConverterRepository
import kotlin.coroutines.CoroutineContext

class GetErrorFromCodeUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: ErrorConverterRepository,
) : BaseUseCase<GetErrorFromCodeUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) {
        return repository.getError(params.code, params.identifier)
    }

    data class Params(val code: Int, val identifier: String)
}