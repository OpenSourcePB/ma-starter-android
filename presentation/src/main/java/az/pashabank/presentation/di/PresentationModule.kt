package az.pashabank.presentation.di

import az.pashabank.presentation.base.LanguageContextWrapper
import az.pashabank.presentation.flow.main.MainViewModel
import az.pashabank.presentation.flow.main.content.MainPageViewModel
import az.pashabank.presentation.flow.main.login.LoginViewModel
import az.pashabank.presentation.flow.splash.SplashViewModel
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