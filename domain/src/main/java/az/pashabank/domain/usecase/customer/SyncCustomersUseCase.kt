package az.pashabank.domain.usecase.customer

import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.CustomerRepository
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