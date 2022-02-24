package digital.pashabank.domain.usecase.language

import digital.pashabank.domain.base.BaseUseCase
import digital.pashabank.domain.constant.AppLanguage
import digital.pashabank.domain.exceptions.ErrorConverter
import digital.pashabank.domain.repository.AppSettingsDataSource
import kotlin.coroutines.CoroutineContext

class SaveLanguageUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val dataSource: AppSettingsDataSource
) : BaseUseCase<AppLanguage, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: AppLanguage) {
        dataSource.setAppLanguage(params)
    }
}