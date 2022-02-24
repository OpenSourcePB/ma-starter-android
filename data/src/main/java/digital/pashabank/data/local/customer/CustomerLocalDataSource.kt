package digital.pashabank.data.local.customer

import digital.pashabank.data.local.customer.model.CustomerLocalDto
import digital.pashabank.data.local.transaction.model.TransactionLocalDto
import kotlinx.coroutines.flow.Flow

interface CustomerLocalDataSource {
    fun getCustomerId(): String
    fun observeCustomers(): Flow<List<CustomerLocalDto>>
    suspend fun insertCustomers(customers: List<CustomerLocalDto>)
}
