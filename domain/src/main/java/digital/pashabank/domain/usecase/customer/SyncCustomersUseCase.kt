package digital.pashabank.domain.usecase.customer

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.CustomerRepository
import kotlin.coroutines.CoroutineContext

class SyncCustomersUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CustomerRepository
) : BaseUseCase<Unit, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Unit) {
        repository.syncCustomers()
    }
}