package az.pashabank.data.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.getDate(inputFormat: String): Date? {
    return SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
}