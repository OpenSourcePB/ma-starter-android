package az.pashabank.data.local.customer

import az.pashabank.data.local.customer.model.CustomerLocalDto
import kotlinx.coroutines.flow.Flow

interface CustomerLocalDataSource {
    fun getCustomerId(): String
    fun observeCustomers(): Flow<List<CustomerLocalDto>>
    suspend fun insertCustomers(customers: List<CustomerLocalDto>)
}
