package az.pashabank.presentation.flow.main.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import az.pashabank.domain.model.customer.Card
import az.pashabank.domain.model.customer.Customer
import az.pashabank.domain.model.customer.Transaction
import az.pashabank.domain.usecase.card.ObserveCardsUseCase
import az.pashabank.domain.usecase.card.SyncCardsUseCase
import az.pashabank.domain.usecase.customer.ObserveCustomerUseCase
import az.pashabank.domain.usecase.customer.SyncCustomersUseCase
import az.pashabank.domain.usecase.transaction.ObserveTransactionsUseCase
import az.pashabank.domain.usecase.transaction.SyncTransactionsUseCase
import az.pashabank.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MainPageViewModel(
    observeCustomerUseCase: ObserveCustomerUseCase,
    syncCustomersUseCase: SyncCustomersUseCase,
    observeCardsUseCase: ObserveCardsUseCase,
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
            }
            .launchNoLoading()

        observeCardsUseCase.execute(Unit)
            .filterNotNull()
            .onEach { postState(MainPageState.ShowCards(it)) }
            .launchNoLoading()

        syncCustomersUseCase.launch(Unit)

        loadCards()
    }

    private fun loadCards() {
        syncCardsUseCase.launch(Unit, loadingHandle = {
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
        syncTransactionsUseCase.launch(
            SyncTransactionsUseCase.Param(cardId),
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