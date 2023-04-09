package az.pashabank.domain.repository

import az.pashabank.domain.model.customer.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun observeTransactions(cardId: String): Flow<List<Transaction>>
    suspend fun syncTransactions(cardId: String)
}