package az.pashabank.data.mapper

import az.pashabank.data.local.card.model.CardLocalDto
import az.pashabank.data.local.customer.model.CustomerLocalDto
import az.pashabank.data.local.transaction.model.TransactionLocalDto
import az.pashabank.data.remote.model.card.CardRemoteDto
import az.pashabank.data.remote.model.customer.CustomerRemoteDto
import az.pashabank.data.remote.model.transaction.TransactionRemoteDto

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
