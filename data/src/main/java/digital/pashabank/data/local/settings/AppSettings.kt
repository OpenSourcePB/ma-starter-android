package digital.pashabank.data.local.settings

import digital.pashabank.domain.constant.AppLanguage
import kotlinx.coroutines.flow.Flow

interface AppSettings {
    fun getAppLanguage(): AppLanguage
    fun setAppLanguage(langCode: AppLanguage)
    fun observeLanguage(): Flow<AppLanguage>
}