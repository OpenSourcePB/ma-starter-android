package az.pashabank.domain.repository

import az.pashabank.domain.model.customer.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun observeCustomers(): Flow<List<Customer>>
    suspend fun syncCustomers()
}