package az.pashabank.domain.exceptions

fun interface ErrorConverter {
    fun convert(t: Throwable): Throwable
}