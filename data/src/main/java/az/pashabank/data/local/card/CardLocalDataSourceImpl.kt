package az.pashabank.data.local.card

import az.pashabank.data.local.card.model.CardLocalDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CardLocalDataSourceImpl(
    private val cardDao: CardDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CardLocalDataSource {

    override fun observeCards(customerId: String): Flow<List<CardLocalDto>> {
        return cardDao.observeCards(customerId)
    }

    override suspend fun insertCards(customerId: String, cards: List<CardLocalDto>) {
        withContext(ioDispatcher) {
            cardDao.clearCards(customerId)
            cardDao.insertCards(cards)
        }
    }
}