package digital.pashabank.data.mapper

import digital.pashabank.data.local.card.model.CardLocalDto
import digital.pashabank.data.local.customer.model.CustomerLocalDto
import digital.pashabank.data.local.transaction.model.TransactionLocalDto
import digital.pashabank.data.remote.model.card.CardRemoteDto
import digital.pashabank.data.remote.model.customer.CustomerRemoteDto
import digital.pashabank.data.remote.model.transaction.TransactionRemoteDto

fun CustomerRemoteDto.toLocal() = CustomerLocalDto(
    id = id,
    name = name,
    phone = phone,
    createdAt = createdAt
)

fun CardRemoteDto.toLocal() = CardLocalDto(
    id = id,
    createdAt = createdAt,
    customerId = customerId,
    currency = currency,
    status = status,
    type = type,
    balance = balance,
    pan = pan
)

fun TransactionRemoteDto.toLocal() = TransactionLocalDto(
    id = id,
    cardId = cardId,
    category = category,
    title = title,
    amount = amount,
    currency = currency,
    createdAt = createdAt
)
