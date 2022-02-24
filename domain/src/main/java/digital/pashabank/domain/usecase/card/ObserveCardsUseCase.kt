package digital.pashabank.domain.usecase.card

import digital.pashabank.domain.base.BaseFlowUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.model.customer.Card
import digital.pashabank.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveCardsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CustomerRepository
) : BaseFlowUseCase<ObserveCardsUseCase.Params, List<Card>>(context, converter) {

    override fun createFlow(params: Params): Flow<List<Card>> {
        return repository.observeCards(params.customerId)
    }

    data class Params(val customerId: String)
}