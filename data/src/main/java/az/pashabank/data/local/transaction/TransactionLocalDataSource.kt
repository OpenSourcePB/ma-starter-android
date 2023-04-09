package az.pashabank.data.local.transaction

import az.pashabank.data.local.transaction.model.TransactionLocalDto
import kotlinx.coroutines.flow.Flow

interface TransactionLocalDataSource {
    fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>>
    suspend fun insertTransactions(cardId: String, transactions: List<TransactionLocalDto>)
}