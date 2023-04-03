package digital.pashabank.presentation.di

import digital.pashabank.presentation.base.LanguageContextWrapper
import digital.pashabank.presentation.flow.main.MainViewModel
import digital.pashabank.presentation.flow.main.content.MainPageViewModel
import digital.pashabank.presentation.flow.main.login.LoginViewModel
import digital.pashabank.presentation.flow.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SplashViewModel(
            splashUseCase = get(),
        )
    }

    viewModel {
        MainPageViewModel(
            observeCustomerUseCase = get(),
            syncCustomersUseCase = get(),
            observeCardsUseCase = get(),
            syncCardsUseCase = get(),
            observeTransactionsUseCase = get(),
            syncTransactionsUseCase = get()
        )
    }

    viewModel {
        MainViewModel(
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    factory { LanguageContextWrapper(dataSource = get()) }
}