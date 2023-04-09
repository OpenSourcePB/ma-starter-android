package az.pashabank.data.remote.model.transaction

import kotlinx.serialization.Serializable

@Serializable
data class TransactionRemoteDto(
    val id: String,
    val cardId: String,
    val category: String,
    val title: String,
    val amount: String,
    val currency: String,
    val createdAt: String
)