package az.pashabank.domain.constant

import java.util.*

enum class AppLanguage {
    AZ, EN;

    companion object {
        fun of(string: String): AppLanguage = values().firstOrNull { it.name == string } ?: EN
    }
}

fun String.toAppLanguage() = AppLanguage.of(uppercase(Locale.getDefault()))