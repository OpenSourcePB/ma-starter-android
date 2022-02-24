package digital.pashabank.presentation.flow.main.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import digital.pashabank.domain.model.customer.Card
import digital.pashabank.domain.model.customer.Customer
import digital.pashabank.domain.model.customer.Transaction
import digital.pashabank.domain.usecase.card.ObserveCardsUseCase
import digital.pashabank.domain.usecase.card.SyncCardsUseCase
import digital.pashabank.domain.usecase.customer.ObserveCustomerUseCase
import digital.pashabank.domain.usecase.customer.SyncCustomersUseCase
import digital.pashabank.domain.usecase.transaction.ObserveTransactionsUseCase
import digital.pashabank.domain.usecase.transaction.SyncTransactionsUseCase
import digital.pashabank.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MainPageViewModel(
    observeCustomerUseCase: ObserveCustomerUseCase,
    syncCustomersUseCase: SyncCustomersUseCase,
    private val observeCardsUseCase: ObserveCardsUseCase,
    private val syncCardsUseCase: SyncCardsUseCase,
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val syncTransactionsUseCase: SyncTransactionsUseCase
) : BaseViewModel<MainPageState, Nothing>() {

    private val _customer = MutableLiveData<Customer>()
    val customer: LiveData<Customer>
        get() = _customer

    private var _isTransactionLoading = MutableLiveData(true)
    val isTransactionLoading: LiveData<Boolean>
        get() = _isTransactionLoading

    private var _activeCard = MutableLiveData<Card>()
    val activeCard: LiveData<Card>
        get() = _activeCard

    private var _activeCardTransactions = MutableLiveData<List<Transaction>>()
    val activeCardTransactions: LiveData<List<Transaction>>
        get() = _activeCardTransactions

    private var _cardSyncLoading = MutableLiveData<Boolean>()
    val cardSyncLoading: LiveData<Boolean>
        get() = _cardSyncLoading

    private var observableJob: Job? = null

    init {
        observeCustomerUseCase.execute(Unit)
            .filterNotNull()
            .onEach {
                _customer.postValue(it)
                observeCards(it.id)
            }
            .launchNoLoading()

        syncCustomersUseCase.launch(Unit)
    }

    private fun observeCards(customerId: String) {
        observeCardsUseCase.execute(ObserveCardsUseCase.Params(customerId))
            .filterNotNull()
            .onEach { postState(MainPageState.ShowCards(it)) }
            .launchNoLoading()

        loadCards(customerId)
    }

    private fun loadCards(customerId: String) {
        syncCardsUseCase.launch(SyncCardsUseCase.Param(customerId), loadingHandle = {
            _cardSyncLoading.postValue(it)
        })
    }

    private fun observeTransactions(cardId: String) {
        observableJob?.cancel()

        observableJob =
            observeTransactionsUseCase.execute(ObserveTransactionsUseCase.Param(cardId))
                .onEach { _activeCardTransactions.postValue(it) }
                .launchNoLoading()

        loadTransactions(cardId)
    }

    private fun loadTransactions(cardId: String) {
        val customerId = _customer.value?.id ?: return

        syncTransactionsUseCase.launch(
            SyncTransactionsUseCase.Param(customerId, cardId),
            loadingHandle = {
                _isTransactionLoading.postValue(it)
            }) {
        }
    }


    fun setActiveCard(card: Card) {
        _activeCard.postValue(card)
        observeTransactions(card.id)
    }

}

sealed class MainPageState {
    class ShowCards(val cards: List<Card>) : MainPageState()
}