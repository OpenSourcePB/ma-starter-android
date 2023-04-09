package az.pashabank.data.remote.model.customer

import kotlinx.serialization.Serializable


@Serializable
data class CustomerRemoteDto(
    val id: String,
    val name: String,
    val phone: String,
    val createdAt: String
)