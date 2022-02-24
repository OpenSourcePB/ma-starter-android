package digital.pashabank.domain.usecase.card

import digital.pashabank.domain.base.BaseFlowUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.model.customer.Card
import digital.pashabank.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveCardsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CardRepository
) : BaseFlowUseCase<Unit, List<Card>>(context, converter) {

    override fun createFlow(params: Unit): Flow<List<Card>> {
        return repository.observeCards()
    }

}