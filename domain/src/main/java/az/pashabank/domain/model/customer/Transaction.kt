package az.pashabank.domain.model.customer

import java.util.*

data class Transaction(
    val id: String,
    val cardId: String,
    val category: ETransactionCategory,
    val title: String,
    val amount: String,
    val currency: String,
    val createdAt: Date
) {

    fun formattedAmount(): String {
        return "-$amount $currency"
    }
}
