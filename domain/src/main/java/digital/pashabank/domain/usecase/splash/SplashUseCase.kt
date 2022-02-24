package digital.pashabank.domain.usecase.splash

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.exceptions.ErrorConverter
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

class SplashUseCase(
    context: CoroutineContext,
    converter: ErrorConverter
) : BaseUseCase<Unit, SplashStatus>(context, converter) {

    override suspend fun executeOnBackground(params: Unit): SplashStatus {
        delay(2000)
        return if ("".isNotEmpty()) {
            SplashStatus.Registered("")
        } else {
            SplashStatus.NotRegistered
        }
    }

}

sealed class SplashStatus {
    class Registered(val phoneNumber: String) : SplashStatus()
    object NotRegistered : SplashStatus()
}