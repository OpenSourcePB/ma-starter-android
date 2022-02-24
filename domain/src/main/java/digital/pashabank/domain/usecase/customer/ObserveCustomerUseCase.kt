package digital.pashabank.domain.usecase.customer

import digital.pashabank.domain.base.BaseFlowUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.model.customer.Customer
import digital.pashabank.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

class ObserveCustomerUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CustomerRepository
) : BaseFlowUseCase<Unit, Customer>(context, converter) {

    override fun createFlow(params: Unit): Flow<Customer> {
        return repository.observeCustomers()
            .filterNotNull()
            .filter { it.isNotEmpty() }
            .map { it.first() }
    }
}