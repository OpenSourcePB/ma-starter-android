package az.pashabank.presentation.tools

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import java.util.*

object Utils {
    fun userShortName(fullName: String): String {
        return fullName.split(" ").take(2)
            .joinToString(separator = "") { text ->
                text.take(1)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            }
    }

    fun userCapFullName(fullName: String): String {
        val words = fullName.lowercase(Locale.getDefault()).split(" ")
        val capWords = words.map { word ->
            word.replaceFirst(word.first(), word.first().uppercaseChar())
        }
        return capWords.joinToString(" ")
    }

    fun updateBoldSpanWithBoldFont(text: CharSequence, color: Int): SpannableString {
        val new = SpannableString(text)
        val spans = new.getSpans(0, new.length, StyleSpan::class.java)
        for (boldSpan in spans) {
            val start: Int = new.getSpanStart(boldSpan)
            val end: Int = new.getSpanEnd(boldSpan)
            new.setSpan(TypefaceSpan("gilroy_bold"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            new.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return new
    }
}