package digital.pashabank.data.local.card

import digital.pashabank.data.local.card.model.CardLocalDto
import kotlinx.coroutines.flow.Flow

interface CardLocalDataSource {
    fun observeCards(customerId: String): Flow<List<CardLocalDto>>
    suspend fun insertCards(customerId: String, cards: List<CardLocalDto>)
}