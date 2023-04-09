package az.pashabank.data.repository

import az.pashabank.data.local.customer.CustomerLocalDataSource
import az.pashabank.data.mapper.toDomain
import az.pashabank.data.mapper.toLocal
import az.pashabank.data.remote.CustomerApi
import az.pashabank.domain.model.customer.Customer
import az.pashabank.domain.repository.CustomerRepository
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