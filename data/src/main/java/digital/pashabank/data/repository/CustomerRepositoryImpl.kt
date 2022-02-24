package digital.pashabank.data.repository

import digital.pashabank.data.local.customer.CustomerLocalDataSource
import digital.pashabank.data.mapper.toDomain
import digital.pashabank.data.mapper.toLocal
import digital.pashabank.data.remote.CustomerApi
import digital.pashabank.domain.model.customer.Card
import digital.pashabank.domain.model.customer.Customer
import digital.pashabank.domain.model.customer.Transaction
import digital.pashabank.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomerRepositoryImpl(
    private val api: CustomerApi,
    private val customerLocalDataSource: CustomerLocalDataSource
) : CustomerRepository {

    override fun observeCustomers(): Flow<List<Customer>> {
        return customerLocalDataSource.observeCustomers()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncCustomers() {
        val remoteCustomers = api.getCustomers()
        val localCustomers = remoteCustomers.map { it.toLocal() }
        customerLocalDataSource.insertCustomers(localCustomers)
    }

    override fun observeCards(customerId: String): Flow<List<Card>> {
        return customerLocalDataSource.observeCards(customerId)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncCards(customerId: String) {
        val remoteCards = api.getCards(customerId)
        val localCards = remoteCards.map { it.toLocal() }
        customerLocalDataSource.insertCards(customerId, localCards)
    }

    override fun observeTransactions(cardId: String): Flow<List<Transaction>> {
        return customerLocalDataSource.observeTransactions(cardId)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncTransactions(customerId: String, cardId: String) {
        val remoteTransactions = api.getTransactions(customerId, cardId)
        val localTransactions = remoteTransactions.map { it.toLocal() }
        customerLocalDataSource.insertTransactions(cardId, localTransactions)
    }

}