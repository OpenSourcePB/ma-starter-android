package az.pashabank.data.remote.model.card

import kotlinx.serialization.Serializable


@Serializable
data class CardRemoteDto(
    val id: String,
    val customerId: String,
    val currency: String,
    val status: String,
    val type: String,
    val balance: String,
    val pan: String,
    val createdAt: String
)