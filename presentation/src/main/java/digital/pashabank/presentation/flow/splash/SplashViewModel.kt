package digital.pashabank.presentation.flow.splash

import digital.pashabank.domain.usecase.splash.SplashStatus
import digital.pashabank.domain.usecase.splash.SplashUseCase
import digital.pashabank.presentation.base.BaseViewModel

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
                    postState(SplashState.ProceedWithOnboarding)
                }
            }
        }
    }
}

sealed class SplashState {
    object ProceedWithOnboarding : SplashState()
    object ProceedWithAuthorization : SplashState()
}