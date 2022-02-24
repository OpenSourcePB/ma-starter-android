package digital.pashabank.domain.usecase.language

import digital.pashabank.domain.base.BaseFlowUseCase
import digital.pashabank.domain.constant.AppLanguage
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.AppSettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveAppLanguageUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val dataSource: AppSettingsDataSource,
): BaseFlowUseCase<Unit, AppLanguage>(context, converter) {

    override fun createFlow(params: Unit): Flow<AppLanguage> = dataSource.observeLanguage()
}