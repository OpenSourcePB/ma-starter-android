package digital.pashabank.domain.repository

import digital.pashabank.domain.model.customer.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun observeCards(): Flow<List<Card>>
    suspend fun syncCards()
}