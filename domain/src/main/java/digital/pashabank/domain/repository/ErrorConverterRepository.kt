package digital.pashabank.domain.repository

interface ErrorConverterRepository {
    fun getError(code: Int, identifier: String)
}