package digital.pashabank.data.local.customer

import digital.pashabank.data.local.customer.model.card.CardLocalDto
import digital.pashabank.data.local.customer.model.customer.CustomerLocalDto
import digital.pashabank.data.local.customer.model.transaction.TransactionLocalDto
import kotlinx.coroutines.flow.Flow

interface CustomerLocalDataSource {

    fun observeCustomers(): Flow<List<CustomerLocalDto>>
    suspend fun insertCustomers(customers: List<CustomerLocalDto>)

    fun observeCards(customerId: String): Flow<List<CardLocalDto>>
    suspend fun insertCards(customerId: String, cards: List<CardLocalDto>)

    fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>>
    suspend fun insertTransactions(cardId: String, transactions: List<TransactionLocalDto>)
}
