package digital.pashabank.domain.usecase.transaction

import digital.pashabank.domain.base.BaseFlowUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.model.customer.Transaction
import digital.pashabank.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveTransactionsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: TransactionRepository
) : BaseFlowUseCase<ObserveTransactionsUseCase.Param, List<Transaction>>(context, converter) {

    override fun createFlow(params: Param): Flow<List<Transaction>> {
        return repository.observeTransactions(params.cardId)
    }

    data class Param(val cardId: String)

}