package digital.pashabank.presentation.extensions

import java.util.*

fun String.capitalizeFirstLetter(locale: Locale) =
    this.split(" ").joinToString(" ") { text ->
        text.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                locale
            ) else it.toString()
        }
    }.trimEnd()