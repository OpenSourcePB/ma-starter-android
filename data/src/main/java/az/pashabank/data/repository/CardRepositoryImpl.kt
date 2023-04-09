package az.pashabank.data.repository

import az.pashabank.data.local.card.CardLocalDataSource
import az.pashabank.data.local.customer.CustomerLocalDataSource
import az.pashabank.data.mapper.toDomain
import az.pashabank.data.mapper.toLocal
import az.pashabank.data.remote.CardApi
import az.pashabank.domain.model.customer.Card
import az.pashabank.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardRepositoryImpl(
    private val api: CardApi,
    private val cardLocalDataSource: CardLocalDataSource,
    private val customerLocalDataSource: CustomerLocalDataSource
) : CardRepository {

    override fun observeCards(): Flow<List<Card>> {
        val customerId = customerLocalDataSource.getCustomerId()
        return cardLocalDataSource.observeCards(customerId)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncCards() {
        val customerId = customerLocalDataSource.getCustomerId()
        val remoteCards = api.getCards(customerId)
        val localCards = remoteCards.map { it.toLocal() }
        cardLocalDataSource.insertCards(customerId, localCards)
    }

}