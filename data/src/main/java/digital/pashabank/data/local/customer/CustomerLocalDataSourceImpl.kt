package digital.pashabank.data.local.customer

import digital.pashabank.data.local.customer.model.card.CardLocalDto
import digital.pashabank.data.local.customer.model.customer.CustomerLocalDto
import digital.pashabank.data.local.customer.model.transaction.TransactionLocalDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CustomerLocalDataSourceImpl(
    private val customerDao: CustomerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CustomerLocalDataSource {

    override fun observeCustomers(): Flow<List<CustomerLocalDto>> {
        return customerDao.observeCustomers()
    }

    override suspend fun insertCustomers(customers: List<CustomerLocalDto>) {
        withContext(ioDispatcher) {
            customerDao.insertCustomers(customers)
        }
    }

    override fun observeCards(customerId: String): Flow<List<CardLocalDto>> {
        return customerDao.observeCards(customerId)
    }

    override suspend fun insertCards(customerId: String, cards: List<CardLocalDto>) {
        withContext(ioDispatcher) {
            customerDao.clearCards(customerId)
            customerDao.insertCards(cards)
        }
    }

    override fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>> {
        return customerDao.observeTransactions(cardId)
    }

    override suspend fun insertTransactions(
        cardId: String,
        transactions: List<TransactionLocalDto>
    ) {
        withContext(ioDispatcher) {
            customerDao.clearTransactions(cardId)
            customerDao.insertTransactions(transactions)
        }
    }
}