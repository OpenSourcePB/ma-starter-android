package az.pashabank.domain.usecase.language

import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.constant.AppLanguage
import az.pashabank.domain.exceptions.ErrorConverter
import az.pashabank.domain.repository.AppSettingsDataSource
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