package az.pashabank.data.local.transaction

import az.pashabank.data.local.transaction.model.TransactionLocalDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TransactionLocalDataSourceImpl(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TransactionLocalDataSource {


    override fun observeTransactions(cardId: String): Flow<List<TransactionLocalDto>> {
        return transactionDao.observeTransactions(cardId)
    }

    override suspend fun insertTransactions(
        cardId: String,
        transactions: List<TransactionLocalDto>
    ) {
        withContext(ioDispatcher) {
            transactionDao.clearTransactions(cardId)
            transactionDao.insertTransactions(transactions)
        }
    }
}