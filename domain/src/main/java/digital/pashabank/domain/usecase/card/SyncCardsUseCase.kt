package digital.pashabank.domain.usecase.card

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.CardRepository
import kotlin.coroutines.CoroutineContext

class SyncCardsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CardRepository
) : BaseUseCase<Unit, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Unit) {
        repository.syncCards()
    }
}