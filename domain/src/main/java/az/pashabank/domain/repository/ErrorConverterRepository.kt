package az.pashabank.domain.repository

interface ErrorConverterRepository {
    fun getError(code: Int, identifier: String)
}