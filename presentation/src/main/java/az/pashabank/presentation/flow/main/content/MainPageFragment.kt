package az.pashabank.presentation.flow.main.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import az.pashabank.starter.presentation.databinding.FragmentMainPageBinding
import az.pashabank.domain.model.customer.Card
import az.pashabank.presentation.base.BaseFragment
import az.pashabank.presentation.extensions.gone
import az.pashabank.presentation.extensions.invisible
import az.pashabank.presentation.extensions.visible
import az.pashabank.presentation.flow.main.content.adapters.CardsPageAdapter
import az.pashabank.presentation.flow.main.content.adapters.TransactionsAdapter
import az.pashabank.presentation.tools.Utils
import kotlin.reflect.KClass

class MainPageFragment :
    BaseFragment<MainPageState, Nothing, MainPageViewModel, FragmentMainPageBinding>() {

    override val vmClazz: KClass<MainPageViewModel>
        get() = MainPageViewModel::class
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainPageBinding
        get() = FragmentMainPageBinding::inflate
    override val screenName: String
        get() = "main_page"

    private val cardsPageAdapter: CardsPageAdapter by lazy {
        CardsPageAdapter { card ->
            viewModel.setActiveCard(card)
        }
    }

    private val transactionAdapter: TransactionsAdapter by lazy {
        TransactionsAdapter()
    }

    override val bindViews: FragmentMainPageBinding.() -> Unit = {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        viewModel.customer.observe { customer ->
            if (customer.name.isNotEmpty()) {
                avatarLayout.visible()
                textName.visible()
                shortFullName.text = Utils.userShortName(customer.name)
                textName.text = Utils.userCapFullName(customer.name)
            } else {
                avatarLayout.invisible()
                textName.invisible()
            }
        }

        recyclerCards.adapter = cardsPageAdapter

        transactionList.adapter = transactionAdapter
        viewModel.activeCardTransactions.observe {
            transactionAdapter.setData(it)
            updateTransactionListUI()
        }

        viewModel.activeCard.observe {
            cardsPageAdapter.selectedCard = it
        }

        viewModel.isTransactionLoading.observe(viewLifecycleOwner) {
            transactionAdapter.isLoading = it
            updateTransactionListUI()
        }

        viewModel.cardSyncLoading.observe(viewLifecycleOwner) { isLoading ->
            cardsPageAdapter.setLoading(isLoading)
        }
    }

    private fun updateViewPagerAdapter(cards: List<Card>) {
        cardsPageAdapter.setData(cards)
        cardsPageAdapter.getFirstCard()?.let { card ->
            viewModel.setActiveCard(card)
        }
    }

    override fun observeState(state: MainPageState) {
        when (state) {
            is MainPageState.ShowCards -> {
                binding.progressLayout.gone()
                binding.recyclerCards.isVisible = state.cards.isEmpty().not()
                binding.transactionList.isVisible = state.cards.isEmpty().not()
                updateViewPagerAdapter(state.cards)
            }
        }
    }

    private fun updateTransactionListUI() {

    }
}