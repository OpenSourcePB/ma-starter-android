package az.pashabank.domain.usecase.card

import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.CardRepository
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