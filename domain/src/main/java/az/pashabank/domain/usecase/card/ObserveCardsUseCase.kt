package az.pashabank.domain.usecase.card

import az.pashabank.domain.base.BaseFlowUseCase
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.model.customer.Card
import az.pashabank.domain.repository.CardRepository
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