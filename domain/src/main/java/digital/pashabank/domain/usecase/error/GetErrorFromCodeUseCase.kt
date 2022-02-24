package digital.pashabank.domain.usecase.error

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.ErrorConverterRepository
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