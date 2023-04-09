package az.pashabank.domain.di

import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.exceptions.ErrorConverterImpl
import az.pashabank.domain.usecase.card.ObserveCardsUseCase
import az.pashabank.domain.usecase.card.SyncCardsUseCase
import az.pashabank.domain.usecase.customer.ObserveCustomerUseCase
import az.pashabank.domain.usecase.customer.SyncCustomersUseCase
import az.pashabank.domain.usecase.error.GetErrorFromCodeUseCase
import az.pashabank.domain.usecase.login.LoginUseCase
import az.pashabank.domain.usecase.splash.SplashUseCase
import az.pashabank.domain.usecase.transaction.ObserveTransactionsUseCase
import az.pashabank.domain.usecase.transaction.SyncTransactionsUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val IO_CONTEXT = "IO_CONTEXT"
const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"

val domainModule = module {
    single<ErrorConverter> {
        ErrorConverterImpl(
            setOf(
                get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }

    factory {
        SplashUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get()
        )
    }

    factory {
        GetErrorFromCodeUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        ObserveCustomerUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        SyncCustomersUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        ObserveCardsUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        SyncCardsUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        ObserveTransactionsUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        SyncTransactionsUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }

    factory {
        LoginUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get()
        )
    }
}
