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