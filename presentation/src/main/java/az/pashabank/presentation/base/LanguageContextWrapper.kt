package az.pashabank.presentation.base

import android.content.Context
import android.content.res.Configuration
import az.pashabank.domain.repository.AppSettingsDataSource
import java.util.*


class LanguageContextWrapper(private val dataSource: AppSettingsDataSource) {

    fun applyLanguage(c: Context): Context = updateResources(c, dataSource.getAppLanguage().name)

    private fun updateResources(context: Context, language: String): Context {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(language.lowercase(Locale.getDefault())))
        return context.createConfigurationContext(config)
    }
}
