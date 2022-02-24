package digital.pashabank.data.repository

import digital.pashabank.data.local.customer.CustomerLocalDataSource
import digital.pashabank.data.local.transaction.TransactionLocalDataSource
import digital.pashabank.data.mapper.toDomain
import digital.pashabank.data.mapper.toLocal
import digital.pashabank.data.remote.TransactionApi
import digital.pashabank.domain.model.customer.Transaction
import digital.pashabank.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val api: TransactionApi,
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val customerLocalDataSource: CustomerLocalDataSource
) : TransactionRepository {

    override fun observeTransactions(cardId: String): Flow<List<Transaction>> {
        return transactionLocalDataSource.observeTransactions(cardId)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun syncTransactions(cardId: String) {
        val remoteTransactions =
            api.getTransactions(customerLocalDataSource.getCustomerId(), cardId)
        val localTransactions = remoteTransactions.map { it.toLocal() }
        transactionLocalDataSource.insertTransactions(cardId, localTransactions)
    }

}