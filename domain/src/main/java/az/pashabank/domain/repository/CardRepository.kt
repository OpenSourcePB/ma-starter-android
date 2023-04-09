package az.pashabank.domain.repository

import az.pashabank.domain.model.customer.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun observeCards(): Flow<List<Card>>
    suspend fun syncCards()
}