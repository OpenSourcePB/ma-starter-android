package digital.pashabank.domain.usecase.card

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.CustomerRepository
import kotlin.coroutines.CoroutineContext

class SyncCardsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CustomerRepository
) : BaseUseCase<SyncCardsUseCase.Param, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Param) {
        repository.syncCards(params.customerId)
    }

    data class Param(val customerId: String)
}