package az.pashabank.domain.repository

import az.pashabank.domain.constant.AppLanguage
import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    fun getAppLanguage(): AppLanguage
    fun setAppLanguage(langCode: AppLanguage)
    fun observeLanguage(): Flow<AppLanguage>
}