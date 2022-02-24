package digital.pashabank.domain.exceptions

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}