package az.pashabank.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.getFormattedText(langCode: String = Locale.ENGLISH.toLanguageTag()): String {
    val inputDate = Calendar.getInstance()
    inputDate.time = this

    val inputYear = inputDate.get(Calendar.YEAR)
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    val sdf =
        SimpleDateFormat(
            if (inputYear == currentYear) "dd MMMM, HH:mm" else "dd MMMM yyyy",
            Locale(langCode)
        )
    return sdf.format(this).capitalizeFirstLetter(Locale(langCode))
}