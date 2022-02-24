package digital.pashabank.domain.repository

import digital.pashabank.domain.constant.AppLanguage
import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    fun getAppLanguage(): AppLanguage
    fun setAppLanguage(langCode: AppLanguage)
    fun observeLanguage(): Flow<AppLanguage>
}