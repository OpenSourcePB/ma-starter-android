package digital.pashabank.domain.repository

import digital.pashabank.domain.model.customer.Card
import digital.pashabank.domain.model.customer.Customer
import digital.pashabank.domain.model.customer.Transaction
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun observeCustomers(): Flow<List<Customer>>
    suspend fun syncCustomers()

    fun observeCards(customerId: String): Flow<List<Card>>
    suspend fun syncCards(customerId: String)

    fun observeTransactions(cardId: String): Flow<List<Transaction>>
    suspend fun syncTransactions(customerId: String, cardId: String)

}