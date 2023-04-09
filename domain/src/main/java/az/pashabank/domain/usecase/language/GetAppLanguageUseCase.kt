package az.pashabank.domain.usecase.language

import az.pashabank.domain.base.BaseFlowUseCase
import az.pashabank.domain.constant.AppLanguage
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.AppSettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveAppLanguageUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val dataSource: AppSettingsDataSource,
): BaseFlowUseCase<Unit, AppLanguage>(context, converter) {

    override fun createFlow(params: Unit): Flow<AppLanguage> = dataSource.observeLanguage()
}