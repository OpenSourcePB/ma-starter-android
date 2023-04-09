package az.pashabank.presentation.flow.splash

import az.pashabank.domain.usecase.splash.SplashStatus
import az.pashabank.domain.usecase.splash.SplashUseCase
import az.pashabank.presentation.base.BaseViewModel

class SplashViewModel(
    splashUseCase: SplashUseCase,
) : BaseViewModel<SplashState, Nothing>() {

    init {
        launchAll(loadingHandle = {}) {
            when (splashUseCase.getWith(Unit)) {
                is SplashStatus.Registered -> {
                    postState(SplashState.ProceedWithAuthorization)
                }
                is SplashStatus.NotRegistered -> {
                    postState(SplashState.ProceedWithOnBoarding)
                }
            }
        }
    }
}

sealed class SplashState {
    object ProceedWithOnBoarding : SplashState()
    object ProceedWithAuthorization : SplashState()
}