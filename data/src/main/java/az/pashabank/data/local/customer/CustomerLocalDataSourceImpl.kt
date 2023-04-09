package az.pashabank.data.local.customer

import az.pashabank.data.local.customer.model.CustomerLocalDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CustomerLocalDataSourceImpl(
    private val customerDao: CustomerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CustomerLocalDataSource {

    override fun getCustomerId(): String {
        return "1" // mock
    }

    override fun observeCustomers(): Flow<List<CustomerLocalDto>> {
        return customerDao.observeCustomers()
    }

    override suspend fun insertCustomers(customers: List<CustomerLocalDto>) {
        withContext(ioDispatcher) {
            customerDao.insertCustomers(customers)
        }
    }
}