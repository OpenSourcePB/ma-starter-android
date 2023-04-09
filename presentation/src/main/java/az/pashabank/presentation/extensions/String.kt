package az.pashabank.presentation.extensions

import android.text.TextUtils
import android.util.Patterns
import java.util.*

fun String.capitalizeFirstLetter(locale: Locale) =
    this.split(" ").joinToString(" ") { text ->
        text.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                locale
            ) else it.toString()
        }
    }.trimEnd()

fun String.isValidEmail(): Boolean {
    return (!TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches())
}