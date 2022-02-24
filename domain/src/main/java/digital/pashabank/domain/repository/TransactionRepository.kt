package digital.pashabank.domain.repository

import digital.pashabank.domain.model.customer.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun observeTransactions(cardId: String): Flow<List<Transaction>>
    suspend fun syncTransactions(cardId: String)
}