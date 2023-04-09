package az.pashabank.data.mapper

import az.pashabank.data.extensions.getDate
import az.pashabank.data.local.card.model.CardLocalDto
import az.pashabank.data.local.customer.model.CustomerLocalDto
import az.pashabank.data.local.transaction.model.TransactionLocalDto
import az.pashabank.domain.model.customer.*
import az.pashabank.domain.model.customer.Currency
import java.math.BigDecimal
import java.util.*

fun CustomerLocalDto.toDomain() = Customer(
    id = id,
    name = name,
    phone = phone,
    createdAt = createdAt
)

fun CardLocalDto.toDomain() = Card(
    id = id,
    customerId = customerId,
    currency = Currency.valueOf(currency),
    status = ECardStatus.getCardStatus(status),
    type = ECardType.getCardType(type),
    balance = try {
        BigDecimal(balance)
    } catch (e: NumberFormatException) {
        BigDecimal.ZERO
    },
    pan = pan,
    createdAt = createdAt
)

fun TransactionLocalDto.toDomain() = Transaction(
    id = id,
    cardId = cardId,
    category = ETransactionCategory.getCategory(category),
    title = title,
    createdAt = createdAt.getDate("yyyy-MM-dd'T'HH:mm:ss") ?: Date(),
    amount = amount,
    currency = currency
)
