package az.pashabank.data.local.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import az.pashabank.data.local.base.BasePreferences
import az.pashabank.domain.constant.AppLanguage
import az.pashabank.domain.constant.toAppLanguage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AppPreferences(context: Context) : BasePreferences(context), AppSettings {

    private val defaultLocale
        get() = "az"

    override val filename = "app_settings"

    override fun getAppLanguage(): AppLanguage {
        val lang = prefs.getString(LOCALE_KEY, defaultLocale) ?: defaultLocale
        return lang.toAppLanguage()
    }

    override fun setAppLanguage(langCode: AppLanguage) {
        prefs.edit { putString(LOCALE_KEY, langCode.name) }
    }

    override fun observeLanguage(): Flow<AppLanguage> = callbackFlow {
        // initial value
        val lang = prefs.getString(LOCALE_KEY, defaultLocale) ?: defaultLocale

        trySend(lang.toAppLanguage()).isSuccess

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, prefsKey ->
            if (LOCALE_KEY == prefsKey) {
                val newLang = prefs.getString(LOCALE_KEY, defaultLocale) ?: defaultLocale
                trySend(newLang.toAppLanguage()).isSuccess
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    private companion object {
        const val LOCALE_KEY = "locale"
    }

}