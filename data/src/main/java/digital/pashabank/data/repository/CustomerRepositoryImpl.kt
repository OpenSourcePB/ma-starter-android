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
        val remoteCustomers = api.getCustomer(customerLocalDataSource.getCustomerId())
        val localCustomers = remoteCustomers.toLocal()
        customerLocalDataSource.insertCustomers(listOf(localCustomers))
    }
}