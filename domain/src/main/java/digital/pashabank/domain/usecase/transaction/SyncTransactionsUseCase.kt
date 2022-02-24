package digital.pashabank.domain.usecase.transaction

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.CustomerRepository
import kotlin.coroutines.CoroutineContext

class SyncTransactionsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CustomerRepository
) : BaseUseCase<SyncTransactionsUseCase.Param, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Param) {
        repository.syncTransactions(params.customerId, params.cardId)
    }

    data class Param(val customerId: String, val cardId: String)
}